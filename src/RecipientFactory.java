public class RecipientFactory {
    public OfficialRecipient createOfficialRecipient(String name, String email, String designation) {
        return new OfficialRecipient(name, email, designation);
    }

    public OfficialFriend createOfficialFriend(String name, String email, String designation, String birthday) {
        return new OfficialFriend(name, email, designation, birthday);
    }

    public PersonalRecipient createPersonalRecipient(String name, String nickname, String email, String birthday) {
        return new PersonalRecipient(name, nickname, email, birthday);
    }
}
