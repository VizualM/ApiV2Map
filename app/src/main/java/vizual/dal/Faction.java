package vizual.dal;

/**
 * Created by swater on 12/02/2015.
 */
public class Faction {

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    private String faction;

   public Faction(String factionS){
       this.faction = factionS;
   }

    public Faction(){
    }

}
