package pujak.boardgames.secretHitler.core.models;

import pujak.boardgames.secretHitler.core.models.enums.Party;
import pujak.boardgames.secretHitler.core.models.enums.ResponsibilityType;

public class Role {

    private ResponsibilityType responsibilityType;

    public ResponsibilityType getResponsibilityType() {
        return responsibilityType;
    }

    private Party party;

    public Party getParty() {
        return party;
    }

    public Role(ResponsibilityType responsibilityType, Party party) {
        this.responsibilityType = responsibilityType;
        this.party = party;
    }
}
