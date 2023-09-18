public abstract class OfficialPersonnel extends Recipient {

    private String designation;

    public OfficialPersonnel(String name,String email, String designation){
        super(name,email);
        this.designation=designation;
    }

    public String getDesignation() {
        return designation;
    }

}