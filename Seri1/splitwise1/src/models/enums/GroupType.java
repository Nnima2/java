package models.enums;

/*
Explanation:
- In our app, groups have some types that are constants.
- In these cases, we use enums to define them and use them in our code.
- put those types here and use them in your code.
 */
public enum GroupType {
    HomeType( "Home"),
    TripType(  "Trip"),
    FamilyType("Zan-o-Bache"),
    OtherType("Other");

    private final String description;

    GroupType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static GroupType fromDescription(String description) {
        for (GroupType type : GroupType.values()) {
            if (type.getDescription().equalsIgnoreCase(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with description: " + description);
    }

}
