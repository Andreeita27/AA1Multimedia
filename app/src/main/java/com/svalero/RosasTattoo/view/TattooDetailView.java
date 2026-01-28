package com.svalero.RosasTattoo.view;

import com.svalero.RosasTattoo.db.AppDatabase;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.svalero.RosasTattoo.R;
import com.svalero.RosasTattoo.contract.TattooDetailContract;
import com.svalero.RosasTattoo.db.LocalImage;
import com.svalero.RosasTattoo.domain.Tattoo;
import com.svalero.RosasTattoo.presenter.TattooDetailPresenter;

public class TattooDetailView extends BaseView implements TattooDetailContract.View {

    public static final String EXTRA_TATTOO_ID = "tattoo_id";
    public static final String EXTRA_TATTOO_STYLE = "tattoo_style";
    public static final String EXTRA_TATTOO_DESC = "tattoo_desc";
    public static final String EXTRA_TATTOO_IMAGE = "tattoo_image";

    public static final String EXTRA_CLIENT_ID = "client_id";
    public static final String EXTRA_PROFESSIONAL_ID = "professional_id";
    public static final String EXTRA_TATTOO_DATE = "tattoo_date";

    public static final String EXTRA_SESSIONS = "sessions";
    public static final String EXTRA_COVERUP = "coverup";
    public static final String EXTRA_COLOR = "color";
    public static final String EXTRA_LATITUDE = "latitude";
    public static final String EXTRA_LONGITUDE = "longitude";

    private ImageView ivTattoo;
    private TextView tvStyle;
    private TextView tvDesc;
    private ImageButton btnEditTattoo;
    private ImageButton btnDeleteTattoo;
    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;

    private long tattooId;
    private String style;
    private String desc;
    private String imageUrl;
    private long clientId;
    private long professionalId;
    private String tattooDate;
    private int sessions;
    private boolean coverUp;
    private boolean color;
    private Double latitude;
    private Double longitude;

    private static final double DEFAULT_LAT = 41.648823;
    private static final double DEFAULT_LON = -0.889085;
    private static final double DEFAULT_ZOOM = 12.0;

    private TattooDetailContract.Presenter presenter;

    private ActivityResultLauncher<String[]> pickImageLauncher;
    private EditText currentTattooImageEditText;
    private String selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tattoo_detail_view);

        ivTattoo = findViewById(R.id.ivTattoo);
        tvStyle = findViewById(R.id.tvStyle);
        tvDesc = findViewById(R.id.tvDesc);
        btnEditTattoo = findViewById(R.id.btnEditTattoo);
        btnDeleteTattoo = findViewById(R.id.btnDeleteTattoo);

        mapView = findViewById(R.id.mapView);

        presenter = new TattooDetailPresenter(this);

        Intent intent = getIntent();
        tattooId = intent.getLongExtra(EXTRA_TATTOO_ID, -1);
        style = intent.getStringExtra(EXTRA_TATTOO_STYLE);
        desc = intent.getStringExtra(EXTRA_TATTOO_DESC);
        imageUrl = intent.getStringExtra(EXTRA_TATTOO_IMAGE);
        clientId = intent.getLongExtra(EXTRA_CLIENT_ID, -1);
        professionalId = intent.getLongExtra(EXTRA_PROFESSIONAL_ID, -1);
        tattooDate = intent.getStringExtra(EXTRA_TATTOO_DATE);
        sessions = intent.getIntExtra(EXTRA_SESSIONS, 0);
        coverUp = intent.getBooleanExtra(EXTRA_COVERUP, false);
        color = intent.getBooleanExtra(EXTRA_COLOR, false);

        if (intent.hasExtra(EXTRA_LATITUDE)) {
            double lat = intent.getDoubleExtra(EXTRA_LATITUDE, DEFAULT_LAT);
            latitude = lat;
        }
        if (intent.hasExtra(EXTRA_LONGITUDE)) {
            double lon = intent.getDoubleExtra(EXTRA_LONGITUDE, DEFAULT_LON);
            longitude = lon;
        }

        tvStyle.setText(style);
        tvDesc.setText(desc);

        refreshTattooImage();

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {
                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        try {
                            getContentResolver().takePersistableUriPermission(uri, takeFlags);
                        } catch (Exception ignored) {}

                        selectedImageUri = uri.toString();

                        if (currentTattooImageEditText != null) {
                            currentTattooImageEditText.setText(selectedImageUri);
                        }

                        AppDatabase.getInstance(this)
                                .localImageDao()
                                .upsert(new LocalImage("TATTOO", tattooId, selectedImageUri));

                        refreshTattooImage();

                        Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        initMap();

        btnDeleteTattoo.setOnClickListener(v -> showDeleteDialog());
        btnEditTattoo.setOnClickListener(v -> showEditDialog());
    }

    private void refreshTattooImage() {
        AppDatabase db = AppDatabase.getInstance(this);
        String localUri = db.localImageDao().getImageUri("TATTOO", tattooId);

        String toLoad = (localUri != null && !localUri.isEmpty())
                ? localUri
                : imageUrl;

        Glide.with(this)
                .load(toLoad)
                .centerCrop()
                .into(ivTattoo);
    }

    // MAPBOX
    private void initMap() {
        if (mapView == null) return;

        double lat = (latitude != null) ? latitude : DEFAULT_LAT;
        double lon = (longitude != null) ? longitude : DEFAULT_LON;

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                initializePointAnnotationManager();
                addMarker(lat, lon, "Tattoo location");
                setCameraPosition(lat, lon, DEFAULT_ZOOM);
            }
        });
    }

    private void initializePointAnnotationManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);

        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(
                annotationPlugin,
                new AnnotationConfig()
        );
    }

    private void addMarker(double lat, double lon, String title) {
        if (pointAnnotationManager == null) return;

        PointAnnotationOptions options = new PointAnnotationOptions()
                .withPoint(Point.fromLngLat(lon, lat))
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.mipmap.red_marker_foreground))
                .withTextField(title);

        pointAnnotationManager.create(options);
    }

    private void setCameraPosition(double lat, double lon, double zoom) {
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .center(Point.fromLngLat(lon, lat))
                .zoom(zoom)
                .build();

        mapView.getMapboxMap().setCamera(cameraPosition);
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.confirm_delete))
                .setPositiveButton(getString(R.string.delete), (dialog, which) -> presenter.deleteTattoo(tattooId))
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    private void showEditDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_tattoo, null);

        EditText etStyle = view.findViewById(R.id.etTattooStyle);
        EditText etDesc = view.findViewById(R.id.etTattooDescription);
        EditText etImage = view.findViewById(R.id.etTattooImageUrl);
        EditText etLat = view.findViewById(R.id.etTattooLatitude);
        EditText etLon = view.findViewById(R.id.etTattooLongitude);

        etStyle.setText(style);
        etDesc.setText(desc);

        String localUri = AppDatabase.getInstance(this)
                .localImageDao()
                .getImageUri("TATTOO", tattooId);

        etImage.setText((localUri != null && !localUri.isEmpty()) ? localUri : imageUrl);

        etImage.setFocusable(false);
        etImage.setClickable(true);
        etImage.setOnClickListener(v -> {
            currentTattooImageEditText = etImage;
            pickImageLauncher.launch(new String[]{"image/*"});
        });

        double currentLat = (latitude != null) ? latitude : DEFAULT_LAT;
        double currentLon = (longitude != null) ? longitude : DEFAULT_LON;
        etLat.setText(String.valueOf(currentLat));
        etLon.setText(String.valueOf(currentLon));

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Editar tatuaje")
                .setView(view)
                .setPositiveButton("Guardar", null)
                .setNegativeButton("Cancelar", null)
                .create();

        dialog.setOnShowListener(d ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

                    String newStyle = etStyle.getText().toString().trim();
                    String newDesc = etDesc.getText().toString().trim();
                    String newImage = etImage.getText().toString().trim();

                    String latText = etLat.getText().toString().trim().replace(",", ".");
                    String lonText = etLon.getText().toString().trim().replace(",", ".");

                    if (newStyle.isEmpty() || newDesc.isEmpty()) {
                        Toast.makeText(this, "Estilo y descripción son obligatorios", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (newImage.isEmpty()) newImage = imageUrl;

                    if (tattooDate == null || tattooDate.trim().isEmpty() || clientId <= 0 || professionalId <= 0) {
                        Toast.makeText(this,
                                "Faltan datos obligatorios (cliente/profesional/fecha). Vuelve a abrir el detalle desde la lista.",
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    double newLat;
                    double newLon;

                    try {
                        newLat = Double.parseDouble(latText);
                        newLon = Double.parseDouble(lonText);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Latitud/Longitud inválidas", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (newLat < -90 || newLat > 90 || newLon < -180 || newLon > 180) {
                        Toast.makeText(this, "Coordenadas fuera de rango", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Tattoo updated = Tattoo.builder()
                            .clientId(clientId)
                            .professionalId(professionalId)
                            .tattooDate(tattooDate)
                            .style(newStyle)
                            .tattooDescription(newDesc)
                            .imageUrl(newImage)
                            .sessions(sessions)
                            .coverUp(coverUp)
                            .color(color)
                            .latitude(newLat)
                            .longitude(newLon)
                            .build();

                    presenter.updateTattoo(tattooId, updated);

                    style = newStyle;
                    desc = newDesc;
                    imageUrl = newImage;
                    latitude = newLat;
                    longitude = newLon;

                    tvStyle.setText(style);
                    tvDesc.setText(desc);
                    refreshTattooImage();

                    if (pointAnnotationManager != null) {
                        pointAnnotationManager.deleteAll();
                        addMarker(newLat, newLon, "Tattoo location");
                        setCameraPosition(newLat, newLon, DEFAULT_ZOOM);
                    }

                    dialog.dismiss();
                })
        );

        dialog.setOnDismissListener(d -> currentTattooImageEditText = null);

        dialog.show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        if (message != null && message.toLowerCase().contains("elim")) {
            AppDatabase db = AppDatabase.getInstance(this);
            db.favoriteTattooDao().deleteByTattooId(tattooId);
        }
    }

    @Override
    public void onTattooUpdated(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void closeView() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pointAnnotationManager != null) {
            pointAnnotationManager.deleteAll();
            pointAnnotationManager = null;
        }
    }
}