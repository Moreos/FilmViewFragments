package com.example.filmviewfragments;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class FilmItemGson {


    @SerializedName("id")
    private int id;
    @SerializedName("localized_name")
    private String localized_name;
    @SerializedName("name")
    private String name;
    @SerializedName("year")
    private int year;
    @SerializedName("rating")
    private double rating;
    @SerializedName("image_url")
    private String image_url;
    @SerializedName("description")
    private String description;
    @SerializedName("genres")
    private String [] genres;

    public static final Comparator<FilmItemGson> nameCompare =
            new Comparator<FilmItemGson>() {
                @Override
                public int compare(FilmItemGson o1, FilmItemGson o2) {
                    String o1sy = String.valueOf(o1.getLocalized_name());
                    String o2sy = String.valueOf(o2.getLocalized_name());

                    return o1sy.compareTo(o2sy);
                }
            };

    public FilmItemGson(int id, String localized_name, String name, int year, double rating, String image_url, String description) {
        this.id = id;
        this.localized_name = localized_name;
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.image_url = image_url;
        this.description = description;
    }



    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The localized_name
     */
    public String getLocalized_name() {
        return localized_name;
    }

    /**
     *
     * @param localized_name
     * The localized_name
     */
    public void setLocalized_name(String localized_name) {
        this.localized_name = localized_name;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The year
     */
    public int getYear() {
        return year;
    }

    /**
     *
     * @param year
     * The year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     *
     * @return
     * The rating
     */
    public double getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The image_url
     */
    public String getImage_url() {
        return image_url;
    }

    /**
     *
     * @param image_url
     * The image_url
     */
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The genres
     */
    public String[] getGenres() {
        return genres;
    }

    /**
     *
     * @param genres
     * The genres
     */
    public void setGenres(String[] genres) {
        this.genres = genres;
    }
}
