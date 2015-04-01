package vizual.parsing.json;

import java.util.List;

/** * Created by Come on 04/03/2015. */
public class Tuile {

    @com.google.gson.annotations.SerializedName("id")
    private String id;

    @com.google.gson.annotations.SerializedName("faction")
    private String faction;

    @com.google.gson.annotations.SerializedName("structure")
    private String structure;

    @com.google.gson.annotations.SerializedName("bottom_right")
    private double[] bottom_right;

    @com.google.gson.annotations.SerializedName("bottom_left")
    private double[] bottom_left;

    @com.google.gson.annotations.SerializedName("top_right")
    private double[] top_right;

    @com.google.gson.annotations.SerializedName("top_left")
    private double[] top_left;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public double[] getBottom_right() {
        return bottom_right;
    }

    public void setBottom_right(double[] bottom_right) {
        this.bottom_right = bottom_right;
    }

    public double[] getBottom_left() {
        return bottom_left;
    }

    public void setBottom_left(double[] bottom_left) {
        this.bottom_left = bottom_left;
    }

    public double[] getTop_right() {
        return top_right;
    }

    public void setTop_right(double[] top_right) {
        this.top_right = top_right;
    }

    public double[] getTop_left() {
        return top_left;
    }

    public void setTop_left(double[] top_left) {
        this.top_left = top_left;
    }
}