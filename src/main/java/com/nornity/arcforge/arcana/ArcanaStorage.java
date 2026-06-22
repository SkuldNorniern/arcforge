package com.nornity.arcforge.arcana;

public class ArcanaStorage {
    private long amount;
    private final long capacity;
    private final long maxTransferRate;

    public ArcanaStorage(long capacity, long maxTransferRate) {
        this.capacity = capacity;
        this.maxTransferRate = maxTransferRate;
    }

    public long getAmount() {
        return amount;
    }

    public long getCapacity() {
        return capacity;
    }

    public long getMaxTransferRate() {
        return maxTransferRate;
    }

    public void setAmount(long amount) {
        this.amount = Math.max(0, Math.min(amount, capacity));
    }

    public long receive(long amount, boolean simulate) {
        long accepted = Math.min(Math.min(amount, maxTransferRate), capacity - this.amount);
        if (accepted <= 0) {
            return 0;
        }
        if (!simulate) {
            this.amount += accepted;
        }
        return accepted;
    }

    public long extract(long amount, boolean simulate) {
        long removed = Math.min(Math.min(amount, maxTransferRate), this.amount);
        if (removed <= 0) {
            return 0;
        }
        if (!simulate) {
            this.amount -= removed;
        }
        return removed;
    }
}
