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

import java.util.Date;
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
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Worldstate extends BaseEntity {

    //~ Instance fields --------------------------------------------------------

    private Integer id;
    private String name;
    private String description;
    private List<Category> categories;
    private String creator;
    private Date created;
    private Transition origintransition;
    private Worldstate parentworldstate;
    private DataItem iccdata;
    private List<Worldstate> childworldstates;
    private List<DataItem> worldstatedata;
}
