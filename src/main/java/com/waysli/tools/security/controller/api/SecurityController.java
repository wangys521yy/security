package com.waysli.tools.security.controller.api;

import com.waysli.tools.security.model.DencryptionDTO;
import com.waysli.tools.security.service.Security;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 传输加解密、签名等相关接口
 *
 * @author waysli
 * @create 2017-10-18 下午2:33
 **/
@RestController
@Api(value = "加解密rest接口")
@RequestMapping("/api/security")
public class SecurityController {

    @ApiOperation(value = "获取临时密钥, 用于数据加密传输", notes = "无需参数")
    @RequestMapping(value = "/getTempKey", method = RequestMethod.GET)
    public Object getTempKey() throws Exception {
        return Security.getSecretKey();
    }

    @ApiOperation(value = "获取对应内容的签名", notes = "要签名的字符串内容")
    @RequestMapping(value = "/getSimpleSignature", method = RequestMethod.GET)
    public Object getSimpleSignature(
        @RequestParam(value = "content", defaultValue = "") String content) throws Exception {
        return Security.getSignature(content);
    }

    @ApiOperation(value = "获取对应内容的签名", notes = "要签名的字符串内容")
    @RequestMapping(value = "/getSignature", method = RequestMethod.POST)
    public Object getServiceInstances(
        @RequestBody String content) throws Exception {
        return Security.getSignature(content);
    }

    @ApiOperation(value = "根据指定的密钥加密文本", notes = "密钥和要加密的文本")
    @RequestMapping(value = "/simpleEncryptData", method = RequestMethod.GET)
    public Object simpleEncryptData(
        @RequestParam(value = "cipherKey", defaultValue = "") String cipherKey,
        @RequestParam(value = "plainText", defaultValue = "") String plainText) throws Exception {
        return Security.encryptData(cipherKey, plainText);
    }

    @ApiOperation(value = "根据指定的密钥加密文本", notes = "密钥和要加密的文本")
    @RequestMapping(value = "/encryptData", method = RequestMethod.POST)
    public Object encryptData(
        @RequestBody DencryptionDTO dto) throws Exception {
        return Security.encryptData(dto.getKey(), dto.getText());
    }

    @ApiOperation(value = "根据指定的密钥解密文本", notes = "密钥和要解密的文本")
    @RequestMapping(value = "/simpleDecryptData", method = RequestMethod.GET)
    public Object simpleDecryptData(
        @RequestParam(value = "cipherKey", defaultValue = "") String cipherKey,
        @RequestParam(value = "cipherText", defaultValue = "") String cipherText) throws Exception {
        return Security.decryptData(cipherKey, cipherText);
    }

    @ApiOperation(value = "根据指定的密钥解密文本", notes = "密钥和要解密的文本")
    @RequestMapping(value = "/decryptData", method = RequestMethod.POST)
    public Object decryptData(
        @RequestBody DencryptionDTO dto) throws Exception {
        return Security.decryptData(dto.getKey(), dto.getText());
    }
}
