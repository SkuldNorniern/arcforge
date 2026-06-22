package com.nornity.arcforge.arcana;

public record ArcanaTransferRules(long maxInsert, long maxExtract, boolean allowInsert, boolean allowExtract) {
    public static ArcanaTransferRules unrestricted(long maxTransfer) {
        return new ArcanaTransferRules(maxTransfer, maxTransfer, true, true);
    }
}
