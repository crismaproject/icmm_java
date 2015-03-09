/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package eu.crismaproject.icmm.icmmhelper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
@Data
@ToString
@EqualsAndHashCode
public final class EntityIdentifier {

    //~ Instance fields --------------------------------------------------------

    private final String domain;
    private final String entityName;
    private final Integer id;
}
