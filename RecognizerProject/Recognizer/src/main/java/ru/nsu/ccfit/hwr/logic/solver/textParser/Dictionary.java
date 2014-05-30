package ru.nsu.ccfit.hwr.logic.solver.textParser;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import ru.nsu.ccfit.hwr.logic.solver.token.Token;
import ru.nsu.ccfit.hwr.logic.solver.tokenFactory.AbstractTokenFactory;
import ru.nsu.ccfit.hwr.logic.solver.tokenFactory.ConstantFactory;


/**
 * Created by user_2 on 21.12.13.
 */
class Dictionary {
    private final TreeMap<String, AbstractTokenFactory> tokenFactoryTreeMap    = new TreeMap<String, AbstractTokenFactory>();
    private final ConstantFactory constantFactory        = new ConstantFactory();
    private final Properties                            properties             = new Properties();

    void load_dictionary (Context context, String filename) {

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(filename);
            Set<String>             stringSet;
            AbstractTokenFactory    tokenFactory;
            Class                   myClass;

            properties.load(inputStream);
            stringSet = properties.stringPropertyNames();

            for (String str : stringSet) {
                myClass = Class.forName(properties.getProperty(str));
                tokenFactory = (AbstractTokenFactory) myClass.newInstance();
                tokenFactoryTreeMap.put(str, tokenFactory);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Token get_token(String token_name) {


        if (tokenFactoryTreeMap.containsKey(token_name)) {
            return tokenFactoryTreeMap.get(token_name).instanceToken(token_name);
        }
        else {
            return constantFactory.instanceToken(token_name);
        }
    }
}
