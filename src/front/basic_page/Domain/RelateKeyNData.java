package front.basic_page.Domain;

/**
 * 描述:
 * 数据和主题词关联Bean
 *
 * @author black-leaves
 * @createTime 2020-06-18  14:44
 */

public class RelateKeyNData {
    private int rela_id;
    private int basi_info_id;
    private int at_key_id;
    private int at_val_id;

    public int getRela_id() {
        return rela_id;
    }

    public void setRela_id(int rela_id) {
        this.rela_id = rela_id;
    }

    public int getBasi_info_id() {
        return basi_info_id;
    }

    public void setBasi_info_id(int basi_info_id) {
        this.basi_info_id = basi_info_id;
    }

    public int getAt_key_id() {
        return at_key_id;
    }

    public void setAt_key_id(int at_key_id) {
        this.at_key_id = at_key_id;
    }

    public int getAt_val_id() {
        return at_val_id;
    }

    public void setAt_val_id(int at_val_id) {
        this.at_val_id = at_val_id;
    }

    @Override
    public String toString() {
        return "RelateKeyNData{" +
                "rela_id=" + rela_id +
                ", basi_info_id=" + basi_info_id +
                ", at_key_id=" + at_key_id +
                ", at_val_id=" + at_val_id +
                '}';
    }
}
