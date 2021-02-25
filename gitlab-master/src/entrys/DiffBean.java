package entrys;

import lombok.*;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
public class DiffBean {
    private String diff;
    private String new_path;
    private String old_path;
    private String a_mode;
    private String b_mode;
    private String new_file;
    private String renamed_file;
    private String deleted_file;
}
