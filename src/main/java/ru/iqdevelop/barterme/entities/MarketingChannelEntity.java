package ru.iqdevelop.barterme.entities;

import javax.persistence.*;

@Entity
@Table(name = "marketing_channels")
public class MarketingChannelEntity {

    @Id
    @SequenceGenerator(name = "marketing_channels_seq", sequenceName = "marketing_marketing_channel_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marketing_channels_seq")
    @Column(name = "marketing_channel_id")
    private Long marketingChannelId;

    @Column(name = "title")
    private String title;

    public Long getMarketingChannelId() {
        return marketingChannelId;
    }

    public void setMarketingChannelId(Long marketingChannelId) {
        this.marketingChannelId = marketingChannelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
