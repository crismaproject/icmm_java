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
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  0.1
 */
@XmlRootElement
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Classification extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    public static final String ENTITY_NAME = "classifications"; // NOI18N

    //~ Instance fields --------------------------------------------------------

    private String key;

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getEntityName() {
        return ENTITY_NAME;
    }
}
