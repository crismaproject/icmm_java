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
public final class DataItem extends BaseEntity {

    //~ Instance fields --------------------------------------------------------

    private Integer id;
    private String name;
    private String description;
    private Date lastmodified;
    private Date temporalcoveragefrom;
    private Date temporalcoverageto;
    private DataDescriptor datadescriptor;
    private String actualaccessinfocontenttype;
    private String actualaccessinfo;
    private SpatialCoverage spatialcoverage;
    private Worldstate worldstate;
}
