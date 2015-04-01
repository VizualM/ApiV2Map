package vizual.dal;

/**
 * Created by swater on 12/02/2015.
 **/
public class Tile {
  private String id;
  public Faction faction= new Faction();
  public Structure stucture = new Structure();
  public GeoPoint BottomLeft = new GeoPoint();
  public GeoPoint BottomRight = new GeoPoint();
  public GeoPoint TopLeft = new GeoPoint();
  public GeoPoint TopRight = new GeoPoint();

    public Tile(String id, Faction faction, Structure stucture, GeoPoint bottomLeft, GeoPoint bottomRight, GeoPoint topLeft, GeoPoint topRight) {
        this.id = id;
        this.faction = faction;
        this.stucture = stucture;
        BottomLeft = bottomLeft;
        BottomRight = bottomRight;
        TopLeft = topLeft;
        TopRight = topRight;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBottomLeft(GeoPoint bottomLeft) {
        BottomLeft = bottomLeft;
    }

    public void setBottomRight(GeoPoint bottomRight) {
        BottomRight = bottomRight;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public void setStucture(Structure stucture) {
        this.stucture = stucture;
    }

    public void setTopLeft(GeoPoint topLeft) {
        TopLeft = topLeft;
    }

    public void setTopRight(GeoPoint topRight) {
        TopRight = topRight;
    }

    public Faction getFaction() {
        return faction;
    }

    public Structure getStucture() {
        return stucture;
    }

    public GeoPoint getBottomLeft() {
        return BottomLeft;
    }

    public GeoPoint getBottomRight() {
        return BottomRight;
    }

    public GeoPoint getTopLeft() {
        return TopLeft;
    }

    public GeoPoint getTopRight() {
        return TopRight;
    }

}
