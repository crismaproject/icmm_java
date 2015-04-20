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
public final class Casualties extends Common {

    //~ Instance fields --------------------------------------------------------

    private Value noOfDeadMin;
    private Value noOfInjuredMin;
    private Value noOfHomelessMin;
    private Value noOfDeadAvg;
    private Value noOfInjuredAvg;
    private Value noOfHomelessAvg;
    private Value noOfDeadMax;
    private Value noOfInjuredMax;
    private Value noOfHomelessMax;
}
