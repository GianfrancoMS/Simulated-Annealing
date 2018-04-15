package com.gianfranco.simulatedannealing.presenter;

import com.gianfranco.simulatedannealing.model.Place;
import com.gianfranco.simulatedannealing.model.Point;
import com.gianfranco.simulatedannealing.view.MainView;

import java.util.Arrays;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {

    private MainView view;

    private final List<Place> places = Arrays.asList(
            new Place(new Point(-16.3445345, -71.5679539), "Rodriguez Ballon"),
            new Place(new Point(-13.713229, -73.352518), "Andahuaylas"),
            new Place(new Point(-9.347222, -77.598333), "Comandante FAP Germán Arias Graciani"),
            new Place(new Point(-13.1535905, -74.2064698), "Coronel FAP Alfredo Mendívil"),
            new Place(new Point(-7.1373045, -78.490404), "Mayor Gral. FAP Armando Revoredo"),
            new Place(new Point(-9.14927, -78.5250945), "Tnte. FAP Jaime De Montruil M."),
            new Place(new Point(-13.5373996, -71.9441935), "Alejandro Velazco Astete"),
            new Place(new Point(-6.2068541, -77.8530181), "Chachapoyas"),
            new Place(new Point(-6.7770364, -79.8298461), "Capitán FAP José Quiñones G."),
            new Place(new Point(-9.8782706, -76.2060759), "Alférez FAP David Figueroa F."),
            new Place(new Point(-17.6926695, -71.3436994), "Ilo"),
            new Place(new Point(-3.7846222, -73.3098907), "Coronel FAP Francisco Secada V."),
            new Place(new Point(-7.1789424, -76.7355252), "Juanjuí"),
            new Place(new Point(-15.4663774, -70.1613253), "Manco Cápac"),
            new Place(new Point(-12.0240527, -77.1142247), "Internacional Jorge Chávez"),
            new Place(new Point(-6.0654945, -77.163018), "Moyobamba"),
            new Place(new Point(-13.745833, -76.2221897), "Pisco"),
            new Place(new Point(-5.2069601, -80.6177466), "Capitán FAP Carlos Concha"),
            new Place(new Point(-8.384761, -74.5760382), "Pucallpa"),
            new Place(new Point(-12.6037306, -69.2236304), "Padre Aldamiz"),
            new Place(new Point(-6.0655811, -77.1648734), "Rioja"),
            new Place(new Point(-18.0530577, -70.2777929), "Coronel FAP Carlos Ciriani"),
            new Place(new Point(-4.5757851, -81.2583956), "Capitán FAP Montes"),
            new Place(new Point(-6.5120492, -76.3712158), "Tarapoto"),
            new Place(new Point(-9.2863581, -76.0072872), "Tingo María"),
            new Place(new Point(-8.0847503, -79.1096385), "Capitán FAP Carlos Martínez Pinillos"),
            new Place(new Point(-8.0847503, -79.1096385), "Capitán FAP Pedro Canga"),
            new Place(new Point(-5.8935929, -76.1214996), "Yurimaguas")
    );

    private final Place peru = new Place(new Point(-9.2155836, -79.5154788), "Peru");

    @Override
    public void onAttach(MainView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        if (view != null) {
            view = null;
        }
    }

    @Override
    public void onLoadPlaces() {
        if (view != null) {
            view.focusMapOnPlace(peru);
            view.drawPlaces(places);
        }
    }
}
