/*
 * Copyright (c) 2025 <project contributors>
 * This program is made available under the terms of the MIT License.
 */
package org.rere.external.aws.dataHolders;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.util.DefaultSdkAutoConstructList;
import software.amazon.awssdk.core.util.DefaultSdkAutoConstructMap;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttributeValueHolder implements Serializable {
    private final String s;
    private final String n;
    private final SdkBytes b;
    private final List<String> ss;
    private final List<String> ns;
    private final List<SdkBytes> bs;
    private final Map<String, AttributeValueHolder> m;
    private final List<AttributeValueHolder> l;
    private final Boolean bool;
    private final Boolean nul;
    private final AttributeValue.Type type;

    public AttributeValueHolder(AttributeValue attributeValue) {
        if (attributeValue.hasBs()) {
            this.bs = new ArrayList<>(attributeValue.bs());
        } else {
            this.bs = null;
        }

        if (attributeValue.hasL()) {
            this.l = new ArrayList<>();
            for (AttributeValue av : attributeValue.l()) {
                l.add(new AttributeValueHolder(av));
            }
        } else {
            this.l = null;
        }

        if (attributeValue.hasM()) {
            this.m = new HashMap<>();
            for (String key : attributeValue.m().keySet()) {
                AttributeValue v = attributeValue.m().get(key);
                this.m.put(key, new AttributeValueHolder(v));
            }
        } else {
            this.m = null;
        }

        if (attributeValue.hasNs()) {
            this.ns = new ArrayList<>(attributeValue.ns());
        } else {
            this.ns = null;
        }

        if (attributeValue.hasSs()) {
            this.ss = new ArrayList<>(attributeValue.ss());
        } else {
            this.ss = null;
        }

        this.nul = attributeValue.nul();
        this.bool = attributeValue.bool();
        this.s = attributeValue.s();
        this.n = attributeValue.n();
        this.b = attributeValue.b();
        this.type = attributeValue.type();
    }

    public AttributeValue revert() {
        List<AttributeValue> lav;
        if (this.l == null) {
            lav = DefaultSdkAutoConstructList.getInstance();
        } else {
            lav = l.stream().map(AttributeValueHolder::revert).collect(Collectors.toList());
        }

        Map<String, AttributeValue> mav;
        if (m == null) {
            mav = DefaultSdkAutoConstructMap.getInstance();
        } else {
            mav = new HashMap<>();
            for (String key : m.keySet()) {
                mav.put(key, m.get(key).revert());
            }
        }

        final List<String> ssR = ss == null ? DefaultSdkAutoConstructList.getInstance() : ss;
        final List<String> nsR = ns == null ? DefaultSdkAutoConstructList.getInstance() : ns;
        final List<SdkBytes> bsR = bs == null ? DefaultSdkAutoConstructList.getInstance() : bs;

        return AttributeValue.builder()
                .s(this.s)
                .n(this.n)
                .b(this.b)
                .ss(ssR)
                .ns(nsR)
                .bs(bsR)
                .m(mav)
                .l(lav)
                .bool(this.bool)
                .nul(this.nul)
                .build();

    }
}
