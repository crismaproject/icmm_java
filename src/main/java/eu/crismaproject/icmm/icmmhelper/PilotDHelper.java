/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package eu.crismaproject.icmm.icmmhelper;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  0.1
 */
public final class PilotDHelper {

    //~ Instance fields --------------------------------------------------------

    private final ICMMClient client;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new PilotDHelper object.
     *
     * @param  apiurl  DOCUMENT ME!
     */
    public PilotDHelper(final String apiurl) {
        this.client = new ICMMClient(apiurl);
    }
}
