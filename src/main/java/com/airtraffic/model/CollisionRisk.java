package com.airtraffic.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Çarpışma riski modeli
 * İki araç arasındaki çarpışma riskini temsil eder
 */
public class CollisionRisk {
    private String vehicleId1;              // İlk araç ID
    private String vehicleId2;              // İkinci araç ID
    private RiskLevel riskLevel;            // Risk seviyesi
    private double riskScore;               // Risk skoru (0.0 - 1.0)
    private double estimatedTimeToCollision; // Tahmini çarpışma süresi (saniye)
    private double currentDistance;          // Mevcut mesafe (metre)
    private double horizontalDistance;       // Yatay mesafe (metre)
    private double verticalDistance;          // Dikey mesafe (metre)
    private String recommendedAction;        // Önerilen aksiyon
    private LocalDateTime detectedAt;        // Tespit zamanı

    public CollisionRisk() {
        this.detectedAt = LocalDateTime.now();
        this.riskScore = 0.0;
        this.estimatedTimeToCollision = Double.MAX_VALUE;
    }

    public CollisionRisk(String vehicleId1, String vehicleId2, RiskLevel riskLevel, double riskScore) {
        this();
        this.vehicleId1 = vehicleId1;
        this.vehicleId2 = vehicleId2;
        this.riskLevel = riskLevel;
        this.riskScore = riskScore;
        // Calculate recommended action based on risk level
        if (riskLevel == null) {
            this.recommendedAction = "No action required";
        } else {
            switch (riskLevel) {
                case LOW:
                    this.recommendedAction = "Continue monitoring";
                    break;
                case MEDIUM:
                    this.recommendedAction = "Increase separation distance";
                    break;
                case HIGH:
                    this.recommendedAction = "Immediate course correction required";
                    break;
                case CRITICAL:
                    this.recommendedAction = "EMERGENCY: Immediate evasive action required";
                    break;
                default:
                    this.recommendedAction = "Monitor situation";
            }
        }
    }

    /**
     * Risk seviyesine göre önerilen aksiyonu belirler
     */
    public void calculateRecommendedAction() {
        if (riskLevel == null) {
            this.recommendedAction = "No action required";
            return;
        }

        switch (riskLevel) {
            case LOW:
                this.recommendedAction = "Continue monitoring";
                break;
            case MEDIUM:
                this.recommendedAction = "Increase separation distance";
                break;
            case HIGH:
                this.recommendedAction = "Immediate course correction required";
                break;
            case CRITICAL:
                this.recommendedAction = "EMERGENCY: Immediate evasive action required";
                break;
            default:
                this.recommendedAction = "Monitor situation";
        }
    }

    /**
     * Kritik risk olup olmadığını kontrol eder
     * @return Kritik risk ise true
     */
    public boolean isCritical() {
        return riskLevel == RiskLevel.CRITICAL;
    }

    /**
     * Yüksek veya kritik risk olup olmadığını kontrol eder
     * @return Yüksek/kritik risk ise true
     */
    public boolean requiresImmediateAction() {
        return riskLevel == RiskLevel.HIGH || riskLevel == RiskLevel.CRITICAL;
    }

    // Getters and Setters

    public String getVehicleId1() {
        return vehicleId1;
    }

    public void setVehicleId1(String vehicleId1) {
        this.vehicleId1 = vehicleId1;
    }

    public String getVehicleId2() {
        return vehicleId2;
    }

    public void setVehicleId2(String vehicleId2) {
        this.vehicleId2 = vehicleId2;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
        calculateRecommendedAction(); // Risk seviyesi değiştiğinde aksiyonu güncelle
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        if (riskScore < 0.0 || riskScore > 1.0) {
            throw new IllegalArgumentException("Risk score must be between 0.0 and 1.0");
        }
        this.riskScore = riskScore;
    }

    public double getEstimatedTimeToCollision() {
        return estimatedTimeToCollision;
    }

    public void setEstimatedTimeToCollision(double estimatedTimeToCollision) {
        if (estimatedTimeToCollision < 0) {
            throw new IllegalArgumentException("Estimated time to collision cannot be negative");
        }
        this.estimatedTimeToCollision = estimatedTimeToCollision;
    }

    public double getCurrentDistance() {
        return currentDistance;
    }

    public void setCurrentDistance(double currentDistance) {
        if (currentDistance < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }
        this.currentDistance = currentDistance;
    }

    public double getHorizontalDistance() {
        return horizontalDistance;
    }

    public void setHorizontalDistance(double horizontalDistance) {
        if (horizontalDistance < 0) {
            throw new IllegalArgumentException("Horizontal distance cannot be negative");
        }
        this.horizontalDistance = horizontalDistance;
    }

    public double getVerticalDistance() {
        return verticalDistance;
    }

    public void setVerticalDistance(double verticalDistance) {
        this.verticalDistance = verticalDistance; // Negatif olabilir (yükseklik farkı)
    }

    public String getRecommendedAction() {
        return recommendedAction;
    }

    public void setRecommendedAction(String recommendedAction) {
        this.recommendedAction = recommendedAction;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollisionRisk that = (CollisionRisk) o;
        return Objects.equals(vehicleId1, that.vehicleId1) &&
               Objects.equals(vehicleId2, that.vehicleId2) &&
               Objects.equals(detectedAt, that.detectedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId1, vehicleId2, detectedAt);
    }

    @Override
    public String toString() {
        return "CollisionRisk{" +
                "vehicleId1='" + vehicleId1 + '\'' +
                ", vehicleId2='" + vehicleId2 + '\'' +
                ", riskLevel=" + riskLevel +
                ", riskScore=" + riskScore +
                ", estimatedTimeToCollision=" + estimatedTimeToCollision +
                ", currentDistance=" + currentDistance +
                ", recommendedAction='" + recommendedAction + '\'' +
                ", detectedAt=" + detectedAt +
                '}';
    }
}

