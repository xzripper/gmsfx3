package gogmsfx3;import("fmt";"io";"net/http";"os";"path/filepath";"strconv";"time");type gmsfx3_download_result struct{p string
e []string};type cached_sfx_buffer struct{b map[string]bool};const GMSFX3_VERSION="v1.2.0"
func gmsfx3_generate_url(sfx_name string)string{return fmt.Sprintf("https://github.com/xzripper/gmsfx3-sounds/blob/main/sounds/%s.wav?raw=true",sfx_name)}
func gmsfx3_get(sfx_name string)gmsfx3_download_result{r,e:=http.Get(gmsfx3_generate_url(sfx_name));if e!=nil{return gmsfx3_download_result{p:"",e:[]string{"e!=nil", e.Error()}}}
defer r.Body.Close();if r.StatusCode!=http.StatusOK{return gmsfx3_download_result{p:"",e:[]string{"code!=ok",strconv.Itoa(r.StatusCode)}}};td:=os.TempDir()
p:=filepath.Join(td,fmt.Sprintf("%s_%d.wav",sfx_name,time.Now().UnixNano()));f,e:=os.Create(p)
if e!=nil{return gmsfx3_download_result{p:"",e:[]string{"e!=nil",e.Error()}}};defer f.Close();_,e=io.Copy(f,r.Body)
if e!=nil{return gmsfx3_download_result{p:"",e:[]string{"e!=nil",e.Error()}}};return gmsfx3_download_result{p:p,e:nil}};
