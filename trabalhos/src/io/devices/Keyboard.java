package io.devices;

import io.Device;
import io.Readable;

public class Keyboard extends Device implements Readable{

    @Override
    public Integer read() {
        return 1;
    }
    
}
