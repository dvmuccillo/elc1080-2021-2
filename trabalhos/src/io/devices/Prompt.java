package io.devices;

import io.Device;
import io.Writable;

public class Prompt extends Device implements Writable {

    @Override
    public void write(Integer value) {
        System.out.println(value);        
    }
    
}
