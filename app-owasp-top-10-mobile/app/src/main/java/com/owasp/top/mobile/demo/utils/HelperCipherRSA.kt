package com.owasp.top.mobile.demo.utils

import com.owasp.top.mobile.demo.utils.cryptii.RSACrypt
import javax.inject.Inject

class HelperCipherRSA @Inject constructor(
    val helperSecure: HelperSecure
){

    /*fun <T> encrypt(data: T?): RequestBase {
        val publicKey = RSACrypt.stringToPublicKey(helperSecure.rsaPublicKey)
        val text = Gson().toJson(data)
        val dataEncrypted = RSACrypt.encrypt(text, publicKey)
        return RequestBase(
            data = dataEncrypted
        )
    }*/


    inline fun <reified T> decrypt(dataEncrypted: String): T {
        val privateKey = RSACrypt.stringToPrivateKey(helperSecure.rsaPrivateKey)

        val test = RSACrypt.decrypt("t+F81S0zspOFpreyQCwROk8OMsDACSkC2kj9i++wY2Ig9Fxpov0DLTckn57EaPtPFseKbt/I8Wj1JptVMoZE5N/uqm2Wbh78Oej5fn8WAXa4/4MHSgImOhQUaaLQbafSqXYvGOLKrBuSSH731nvhtZ0yH8JIvG4O0o4kRVlX6PPAblvml98QCv2l69VLa7EzKoOj1UCoVr25Q4WBblsFYI8vtq3DqGRmkXMocjD6PPTnnx3u+kc6G1JfFHPywOwt+fpSD5OimH6+6UPqwwl12t1XJnQJKiOczj0T0M4MG8DFK0tiKlg5+Xp3SX756XEHUqnKTkpJc5J4jHx13MYYXg==", privateKey)
        println(test)
        val hc = "jH5dbTLwfGJEdXoMOLrtujory+gVbJHu9xsASjLACJcwD3VF4eebaaQ4KD3JcrCNzsXXQ2LaoySpxhiSV6xCSMrbgnhOvCztG/Gs/e3R2nb2Ispk6875pJmV1vam1djQnTqxY7nrv20WmVfIgtT78tlUM1FkwZYWAWIfR5ZZ6N2scdnbxvdzk2zW7g4HIv/5hdlVNyYyn99SldAp/Q9oRZaX17bLRjpyanPd2YNZtz/ERyzJhTrd3qDpSiplbN4AqAckHIflXBnIO45S3mx1lrA3zEZHA8lj0PVW8hRqF382BApu0p/9KdwuglrUXvBFEiUKv+TPVgIDZ3N0loTACJl3eh1s8ZH6I04bP0oLjRLgYbiy178rVa8KV99N0udGcLze15q0mr5KAlfS57B63ED6JpiRX0xuoeq/RWJZ5mj8ak2jhNMWUKh0sshfXb+3uj0qc/b+qj21Car4cWA7OyrUa9RyRKFlHAMPUwpDdiFzWvPlWBymLue2oFfNEKC12whqmq7n2j5cEKwUZLYsUzao0rTKiDqBpXHWp/zuMg4Gh6yTSqzom7wWUGpfE3Erth2OjSiHEkuIrIHA3NABPsp6UX3tlHkolMRtoNNFPKWnrvop0Qj/651qrTp4WT7FrmO9er9VUoxxtCWpQL1dXox2hLk/sTxwIlqbm8SFUNa4bm0uqwsQjs6LbXsnHWTZDtpTX2tP+wKIKtEi2UmjKbsN7XC/9m4/Szqhwecm0Tb1L/IVzwtxuDVdEIeJB9YqIzaHfpjI9p1Qo7EDZSGcEEWZMbK00/90tiidAn+7tPSgmobTLs0eHn8fgg3k86jl/Yb+I5CY5a9A3HM0elL2cQwYxvqKNocxlYTwp5FME2YS8CUAZgCHOpAaDeUPLeYVXeZXY/ULNoqZHSNjC7rOXlylbbWh2qwOfwgFfdMGUKY0WEbYOmkbGlXSA6OPCOLlSxOyN1WXK1gB16k6SDDA8Qui0COzFBXhHLlj5Vjvw44s/lo17hw5ChH75ei6pAms"
        val test2 = RSACrypt.decrypt(hc, privateKey)
        println(test2)
        val textDecrypt = RSACrypt.decrypt(dataEncrypted, privateKey)

        return textDecrypt.toObject()
    }

}