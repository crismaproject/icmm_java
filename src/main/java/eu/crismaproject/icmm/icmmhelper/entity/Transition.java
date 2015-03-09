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

import java.util.List;

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
public class Transition extends BaseEntity {

    //~ Instance fields --------------------------------------------------------

    private Integer id;
    private String name;
    private String description;
    private String transitionstatuscontenttype;
    private String transitionstatus;
    private String performedsimulation;
    private List performedmanipulations;
    private String simulationcontrolparameter;
}
