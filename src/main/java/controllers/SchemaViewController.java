package controllers;

import converters.svgConverter.SvgLoader;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by Влад on 01.11.2017.
 */
public class SchemaViewController {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private StackPane zoomPane;
    @FXML
    private Group group;
    @FXML
    private Group scrollContent;

    private final double SCALE_DELTA = 1.1;
    private final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<Point2D>();

    @FXML
    public void initialize() throws MalformedURLException {

        InputStream svgFile = getClass().getResourceAsStream("/HydrotreaterUnitSimulation.svg");
        SvgLoader loader = new SvgLoader();
        Group svgImage = loader.loadSvg(svgFile);
        group.getChildren().setAll(svgImage);

        scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable,
                                Bounds oldValue, Bounds newValue) {
                zoomPane.setMinSize(newValue.getWidth(), newValue.getHeight());
            }
        });

        VBox.setVgrow(zoomPane, Priority.ALWAYS);
    }
    private Point2D figureScrollOffset(Node scrollContent, ScrollPane scrollPane) {
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scrollPane.getViewportBounds().getWidth();
        double hScrollProportion = (scrollPane.getHvalue() - scrollPane.getHmin()) / (scrollPane.getHmax() - scrollPane.getHmin());
        double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scrollPane.getViewportBounds().getHeight();
        double vScrollProportion = (scrollPane.getVvalue() - scrollPane.getVmin()) / (scrollPane.getVmax() - scrollPane.getVmin());
        double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
        return new Point2D(scrollXOffset, scrollYOffset);
    }

    private void repositionscrollPane(Node scrollContent, ScrollPane scrollPane, double scaleFactor, Point2D scrollOffset) {
        double scrollXOffset = scrollOffset.getX();
        double scrollYOffset = scrollOffset.getY();
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scrollPane.getViewportBounds().getWidth();
        if (extraWidth > 0) {
            double halfWidth = scrollPane.getViewportBounds().getWidth() / 2 ;
            double newScrollXOffset = (scaleFactor - 1) *  halfWidth + scaleFactor * scrollXOffset;
            scrollPane.setHvalue(scrollPane.getHmin() + newScrollXOffset * (scrollPane.getHmax() - scrollPane.getHmin()) / extraWidth);
        } else {
            scrollPane.setHvalue(scrollPane.getHmin());
        }
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scrollPane.getViewportBounds().getHeight();
        if (extraHeight > 0) {
            double halfHeight = scrollPane.getViewportBounds().getHeight() / 2 ;
            double newScrollYOffset = (scaleFactor - 1) * halfHeight + scaleFactor * scrollYOffset;
            scrollPane.setVvalue(scrollPane.getVmin() + newScrollYOffset * (scrollPane.getVmax() - scrollPane.getVmin()) / extraHeight);
        } else {
            scrollPane.setHvalue(scrollPane.getHmin());
        }
    }

    public void zoomPaneOnScroll(ScrollEvent event) {
        event.consume();

        if (event.getDeltaY() == 0) {
            return;
        }

        double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA
                : 1 / SCALE_DELTA;

        // amount of scrolling in each direction in scrollContent coordinate
        // units
        Point2D scrollOffset = figureScrollOffset(scrollContent, scrollPane);

        group.setScaleX(group.getScaleX() * scaleFactor);
        group.setScaleY(group.getScaleY() * scaleFactor);

        // move viewport so that old center remains in the center after the
        // scaling
        repositionscrollPane(scrollContent, scrollPane, scaleFactor, scrollOffset);
    }

    public void scrollPaneOnMouseDragged(MouseEvent event) {
        double deltaX = event.getX() - lastMouseCoordinates.get().getX();
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scrollPane.getViewportBounds().getWidth();
        double deltaH = deltaX * (scrollPane.getHmax() - scrollPane.getHmin()) / extraWidth;
        double desiredH = scrollPane.getHvalue() - deltaH;
        scrollPane.setHvalue(Math.max(0, Math.min(scrollPane.getHmax(), desiredH)));

        double deltaY = event.getY() - lastMouseCoordinates.get().getY();
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scrollPane.getViewportBounds().getHeight();
        double deltaV = deltaY * (scrollPane.getHmax() - scrollPane.getHmin()) / extraHeight;
        double desiredV = scrollPane.getVvalue() - deltaV;
        scrollPane.setVvalue(Math.max(0, Math.min(scrollPane.getVmax(), desiredV)));
    }

    public void scrollPaneOnMousePressed(MouseEvent event) {
        lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
    }
}
