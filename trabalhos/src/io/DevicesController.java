package io;

import java.util.concurrent.atomic.AtomicInteger;

public class DevicesController {
    private Device[] devices;

    public Error read(Integer device, AtomicInteger value){

        final Device targetDevice = devices[device];

        if(!(targetDevice instanceof Device)) return Error.INVALID_DEVICE;
        if(!(targetDevice instanceof Readable)) return Error.UNSUPPORTED_OPERATION;

        final Readable readableDevice = (Readable) targetDevice;
        value.set(readableDevice.read());

        return Error.OK;
    }

    public Error write(Integer device, Integer value){

        final Device targetDevice = devices[device];

        if(!(targetDevice instanceof Device)) return Error.INVALID_DEVICE;
        if(!(targetDevice instanceof Writable)) return Error.UNSUPPORTED_OPERATION;

        final Writable writableDevice = (Writable) targetDevice;
        writableDevice.write(value);

        return Error.OK;
    }
}
