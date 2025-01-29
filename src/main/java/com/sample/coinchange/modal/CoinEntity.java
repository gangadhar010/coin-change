package com.sample.coinchange.modal;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name="COINS")
@Data
public class CoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private long id;

    @Column(name="COIN_TYPE")
    private String coinType;

    @Column(name="AVAILABLE_COINS")
    private Integer availableCoins;

    @Column(name="VERSION")
    @Version
    private Integer version;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
