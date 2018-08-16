# SpringFilesData
---------------------- Funcion javascript ----------------
function callSer() {
                var data = new FormData();
                $.each($('#file')[0].files, function (i, file) {
                    data.append('fotos', file);
                });
                data.append('usuario', 'ismael');
                console.log(data.toString());
                $.ajax({
                    url: 'http://localhost:8084/Sedesoo/.../files',
                    data: data,
                    cache: false,
                    contentType: false,
                    processData: false,
                    method: 'POST',
                    success: function (data) {
                        alert("regesos " + data);
                    }
                    , error: function (datas) {
                        alert("mal " + datas);
                    }
                });
            }


----------------------------------  Html  --------------------------------------------

   
        <input type="file" name="file" id="file">
        <button onclick="callSer()">TOK</button>
