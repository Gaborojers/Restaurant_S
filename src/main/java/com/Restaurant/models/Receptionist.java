package com.Restaurant.models;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Receptionist extends Entity {
    public Receptionist(SpawnData data) {
        // Cargar la imagen
        Image receptionistImage = new Image("/assets/textures/receptionist.png"); // Ruta de la imagen

        // Crear la vista de la entidad usando ImageView
        ImageView imageView = new ImageView(receptionistImage);

        // Configurar la escala de la imagen si es necesario
        imageView.setFitWidth(50); // Ajusta el ancho según sea necesario
        imageView.setFitHeight(50); // Ajusta la altura según sea necesario

        // Construir y adjuntar la entidad con la imagen
        FXGL.entityBuilder(data)
                .view(imageView) // Usar ImageView en lugar de Circle
                .type(EntityType.RECEPTIONIST)
                .buildAndAttach();
    }
}
