package org.kaaproject.kaa.demo.iotworld.citylights.resources;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.JsonDecoder;
import org.apache.avro.specific.SpecificDatumReader;
import org.kaaproject.kaa.client.util.CommonsBase64;
import org.kaaproject.kaa.demo.iotworld.connectedcar.RfidLog;
import org.kaaproject.kaa.demo.iotworld.lights.traffic.TrafficLightsLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerResource.class);
    
    private static final Charset UTF8 = Charset.forName("UTF-8");
    
    private static final DatumReader<RfidLog> rfidReader = new SpecificDatumReader<RfidLog>(RfidLog.SCHEMA$);
    private static final DatumReader<TrafficLightsLog> trafficReader = new SpecificDatumReader<TrafficLightsLog>(TrafficLightsLog.SCHEMA$);

    static RfidLog decodeRfidLog(String reportBody) {
        try {
            JsonDecoder decoder = DecoderFactory.get().jsonDecoder(RfidLog.SCHEMA$, reportBody);
            RfidLog report = null;
            report = rfidReader.read(report, decoder);
            return report;
        } catch (Exception e) {
            LOG.warn("Failed to decode report source {}", reportBody, e);
            return null;
        }
    }

    static TrafficLightsLog decodeTrafficLog(String reportBody) {
        try {
            JsonDecoder decoder = DecoderFactory.get().jsonDecoder(TrafficLightsLog.SCHEMA$, reportBody);
            TrafficLightsLog report = null;
            report = trafficReader.read(report, decoder);
            return report;
        } catch (Exception e) {
            LOG.warn("Failed to decode report source {}", reportBody, e);
            return null;
        }
    }

    static byte[] toByteArray(ByteBuffer buffer) {
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    static Set<String> toTags(List<Long> tags) {
        Set<String> result = new HashSet<>(tags.size());
        for (Long tag : tags) {
            result.add(toTag(tag));
        }
        return result;
    }

    static String toTag(Long tag) {
        return String.valueOf(tag);
    }

}
