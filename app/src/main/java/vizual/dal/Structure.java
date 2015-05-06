package vizual.dal;
/**
 * Created by swater on 12/02/2015.
 */
public class Structure {

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    private String structure;

    public Structure(String structureS){
        this.structure = structureS;
    }

    public Structure(){
    }
}
