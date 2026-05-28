package com.example.starvault.logic;

import com.example.starvault.data.DataAccessLayer;
import com.example.starvault.model.Celebrity;

import java.util.ArrayList;
import java.util.List;

public class CelebrityRepository implements DataAccessLayer<Celebrity> {
    private ArrayList<Celebrity> catalog;
    private static CelebrityRepository self;

    private CelebrityRepository() {
        catalog = new ArrayList<>();
        loadDefaults();
    }

    public static CelebrityRepository get() {
        if (self == null) {
            self = new CelebrityRepository();
        }
        return self;
    }

    private void loadDefaults() {
        catalog.add(new Celebrity("Morgan Freeman",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/Secretary_of_Defense_Lloyd_Austin_hosts_Morgan_Freeman_for_a_private_screening_and_conversation_about_Freeman%E2%80%99s_documentary_film_on_the_761st_Tank_Battalion_at_The_Pentagon_on_Aug._2%2C_2023_-_230802-D-PM193-3363.jpg/500px-thumbnail.jpg",
                4.8f));
        catalog.add(new Celebrity("Scarlett Johansson",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Scarlett_Johansson_293_%28cropped2%29.jpg/250px-Scarlett_Johansson_293_%28cropped2%29.jpg",
                4.7f));
        catalog.add(new Celebrity("Keanu Reeves",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Keanu_Reeves_at_TIFF_2025_02_%28Cropped%29.jpg/250px-Keanu_Reeves_at_TIFF_2025_02_%28Cropped%29.jpg",
                4.9f));
        catalog.add(new Celebrity("Leonardo DiCaprio",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/LeoPTABFI191125-28_%28cropped%29.jpg/250px-LeoPTABFI191125-28_%28cropped%29.jpg",
                4.6f));
        catalog.add(new Celebrity("Natalie Portman",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/NataliePortman.jpg/250px-NataliePortman.jpg",
                4.5f));
        catalog.add(new Celebrity("Tom Hanks",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/3/39/TomHanksPrincEdw031223_%2811_of_41%29_%28cropped%29.jpg/250px-TomHanksPrincEdw031223_%2811_of_41%29_%28cropped%29.jpg",
                4.8f));
        catalog.add(new Celebrity("Gal Gadot",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Gal_Gadot_cropped_lighting_corrected_2b.jpg/250px-Gal_Gadot_cropped_lighting_corrected_2b.jpg",
                4.4f));
        catalog.add(new Celebrity("Denzel Washington",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cc/Denzel_Washington_at_the_2025_Cannes_Film_Festival.jpg/250px-Denzel_Washington_at_the_2025_Cannes_Film_Festival.jpg",
                4.7f));
    }

    @Override
    public boolean insert(Celebrity item) {
        return catalog.add(item);
    }

    @Override
    public boolean refresh(Celebrity item) {
        for (Celebrity c : catalog) {
            if (c.getId() == item.getId()) {
                c.setFullName(item.getFullName());
                c.setPhotoUrl(item.getPhotoUrl());
                c.setScore(item.getScore());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Celebrity item) {
        return catalog.remove(item);
    }

    @Override
    public Celebrity getById(int id) {
        for (Celebrity c : catalog) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    @Override
    public List<Celebrity> getAll() {
        return catalog;
    }
}