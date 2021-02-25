package entrys;

import lombok.*;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
public class FileCommitsBean {
    private String file_name;
    private String modes;

    public FileCommitsBean() {
    }
}
