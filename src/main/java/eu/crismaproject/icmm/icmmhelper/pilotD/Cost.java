/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package eu.crismaproject.icmm.icmmhelper.pilotD;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  0.2
 */
@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class Cost extends Common {

    //~ Instance fields --------------------------------------------------------

    private Value directDamageCostMin;
    private Value indirectDamageCostMin;
    private Value directRestorationCostMin;
    private Value directDamageCostAvg;
    private Value indirectDamageCostAvg;
    private Value directRestorationCostAvg;
    private Value directDamageCostMax;
    private Value indirectDamageCostMax;
    private Value directRestorationCostMax;
}
