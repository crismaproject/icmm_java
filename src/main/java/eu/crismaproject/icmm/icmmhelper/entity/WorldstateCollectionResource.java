/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package eu.crismaproject.icmm.icmmhelper.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */

@XmlRootElement
@NoArgsConstructor
@ToString(
    callSuper = true,
    of = { "$collection" }
)
@EqualsAndHashCode(
    callSuper = true,
    of = { "$collection" }
)
public class WorldstateCollectionResource extends BaseCollectionResource {

    //~ Instance fields --------------------------------------------------------

    @Getter
    @Setter
    private List<Worldstate> $collection;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WorldstateCollectionResource object.
     *
     * @param  $self        DOCUMENT ME!
     * @param  $offset      DOCUMENT ME!
     * @param  $limit       DOCUMENT ME!
     * @param  $first       DOCUMENT ME!
     * @param  $previous    DOCUMENT ME!
     * @param  $next        DOCUMENT ME!
     * @param  $last        DOCUMENT ME!
     * @param  $collection  DOCUMENT ME!
     */
    public WorldstateCollectionResource(final String $self,
            final int $offset,
            final int $limit,
            final String $first,
            final String $previous,
            final String $next,
            final String $last,
            final List<Worldstate> $collection) {
        super($self, $offset, $limit, $first, $previous, $next, $last);

        this.$collection = $collection;
    }
}
