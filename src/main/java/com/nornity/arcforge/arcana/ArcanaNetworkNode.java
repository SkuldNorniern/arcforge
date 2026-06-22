package com.nornity.arcforge.arcana;

import java.util.HashSet;
import java.util.Set;

public class ArcanaNetworkNode {
    private final ArcanaContainer container;
    private final ArcanaTransferRules transferRules;
    private final Set<ArcanaNetworkNode> neighbors = new HashSet<>();

    public ArcanaNetworkNode(ArcanaContainer container, ArcanaTransferRules transferRules) {
        this.container = container;
        this.transferRules = transferRules;
    }

    public ArcanaContainer getContainer() {
        return container;
    }

    public ArcanaTransferRules getTransferRules() {
        return transferRules;
    }

    public Set<ArcanaNetworkNode> getNeighbors() {
        return neighbors;
    }

    public void connect(ArcanaNetworkNode other) {
        neighbors.add(other);
        other.neighbors.add(this);
    }

    public void disconnect(ArcanaNetworkNode other) {
        neighbors.remove(other);
        other.neighbors.remove(this);
    }

    public long distributeTo(ArcanaNetworkNode other, long amount) {
        if (!transferRules.allowExtract() || !other.transferRules.allowInsert()) {
            return 0;
        }
        long allowedOut = Math.min(amount, transferRules.maxExtract());
        long extracted = container.getArcanaStorage().extract(allowedOut, true);
        long allowedIn = Math.min(extracted, other.transferRules.maxInsert());
        long inserted = other.container.getArcanaStorage().receive(allowedIn, false);
        if (inserted > 0) {
            container.getArcanaStorage().extract(inserted, false);
        }
        return inserted;
    }
}
