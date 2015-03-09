/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package eu.crismaproject.icmm.icmmhelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  0.1
 */
@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class TransitionStatus {

    //~ Instance fields --------------------------------------------------------

    private String status;
    private Integer step;
    private Integer maxSteps;
    private String stepName;
}
