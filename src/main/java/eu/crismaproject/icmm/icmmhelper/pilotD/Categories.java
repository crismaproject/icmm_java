/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package eu.crismaproject.icmm.icmmhelper.pilotD;

import eu.crismaproject.icmm.icmmhelper.entity.Category;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  0.1
 */
public enum Categories {

    //~ Instance fields --------------------------------------------------------

    //J-
    WMS_CAPABILITIES("1"),
    SUPPORTIVE_WMS("2"),
    INTENSITY_GRID("3"),
    BUILDING_DAMAGE_MIN("4"),
    BUILDING_DAMAGE_MAX("5"),
    BUILDING_DAMAGE_AVG("6"),
    BUILDING_INVENTORY("7"),
    PEOPLE_DISTRIBUTION("8"),
    PEOPLE_IMPACT_MIN("9"),
    PEOPLE_IMPACT_MAX("10"),
    PEOPLE_IMPACT_AVG("11"),
    SCHEMA("12"),
    RETROFIT_ANALYSIS("13"),
    EVACUATION_ANALYSIS("14"),
    BACKGROUND_LAYER("15"),
    SHAKEMAP("16"),
    INDICATORS("17");
    //J+

    private final String id;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Categories object.
     *
     * @param  id  DOCUMENT ME!
     */
    private Categories(final String id) {
        this.id = id;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Category getCategory() {
        final Category cat = new Category();
        cat.set$ref("/CRISMA.categories/" + id); // NOI18N

        return cat;
    }
}
