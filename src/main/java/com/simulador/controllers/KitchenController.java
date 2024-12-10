package com.simulador.controllers;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class KitchenController implements EntityFactory {
    public static final double KITCHEN_X = 450;
    public static final double KITCHEN_Y = 0;

    @Spawns("kitchen")
    public Entity spawnKitchen(SpawnData data) {
        ImageView imageView = new ImageView(new Image("img/Cocina/cocina.png"));
        imageView.setFitWidth(RestaurantController.SPRITE_SIZE * 9);
        imageView.setFitHeight(RestaurantController.SPRITE_SIZE * 12);

        return entityBuilder()
                .at(data.getX(), data.getY())
                .view(imageView)
                .build();
    }
}
