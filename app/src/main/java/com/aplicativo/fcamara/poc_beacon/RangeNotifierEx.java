package com.aplicativo.fcamara.poc_beacon;

import android.content.Context;
import android.util.Range;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public abstract class RangeNotifierEx implements RangeNotifier {

    protected BeaconConsumer beaconConsumer;

    public RangeNotifierEx(BeaconConsumer beaconConsumer)
    {
        this.beaconConsumer = beaconConsumer;
    }

    public abstract void didRangeBeaconsInRegion(Collection<Beacon> var1, Region var2);
}
