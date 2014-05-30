package ru.nsu.ccfit.hwr.logic.solver.structures;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by user_2 on 22.12.13.
 */
class StructureFactory {
    public Structure instanceStructure (String name) {
        String fileName = "structureFactoryConfig";
        Properties props = new Properties();
        Structure structure = null;
        FileInputStream ins;

        try {
            ins = new FileInputStream(fileName);
            props.load(ins);
            String buf = props.getProperty(name);
            structure = (Structure) Class.forName(buf).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return structure;
    }
}
