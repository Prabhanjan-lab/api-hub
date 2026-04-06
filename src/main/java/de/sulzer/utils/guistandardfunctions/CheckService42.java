/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import java.io.File;

/**
 * @author Sulzer GmbH
 *
 */
public class CheckService42 {

    /**
     *
     */
    public CheckService42() {

    }

    public String getFlashFileName(String absolutePathFlashFile) {
        return new File(absolutePathFlashFile).getName();
    }

}
