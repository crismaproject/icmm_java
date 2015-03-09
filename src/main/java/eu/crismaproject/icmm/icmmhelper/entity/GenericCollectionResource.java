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
import java.util.Map;

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
public class GenericCollectionResource extends BaseCollectionResource {

    //~ Instance fields --------------------------------------------------------

    @Getter
    @Setter
    private List<Map> $collection;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GenericCollectionResource object.
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
    public GenericCollectionResource(final String $self,
            final int $offset,
            final int $limit,
            final String $first,
            final String $previous,
            final String $next,
            final String $last,
            final List<Map> $collection) {
        super($self, $offset, $limit, $first, $previous, $next, $last);

        this.$collection = $collection;
    }
}
