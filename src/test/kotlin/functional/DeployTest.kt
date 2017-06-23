package functional

import com.fasterxml.jackson.databind.ObjectMapper
import com.jessecoyle.JCredStash
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import java.net.HttpURLConnection
import java.net.URL
import java.io.InputStreamReader
import java.io.BufferedReader
import sun.net.www.protocol.https.HttpsURLConnectionImpl
import java.math.BigInteger


class DeployTest {
    var token = ""
    val ENVIRONMENT = System.getenv("ENV") ?: "test"
    // To do the deploy test before submitting a pull request, change testUrl to "http://localhost:8080"
    var testUrl = "https://xref-api.sps$ENVIRONMENT.in"
    var testDataXrefId = ""
    var testVendorXrefId = ""
    var testTurnaroundXrefId = ""
    var dataXrefLookupIdResult = ""
    var vendorXrefLookupIdResult = ""
    var turnaroundXrefLookupIdResult = ""
    var dataXrefLookupKeyResult = ""
    var vendorXrefLookupKeyResult = ""
    var turnaroundXrefLookupKey3Result = ""
    var dataXrefLookupValueResult = ""
    var vendorXrefLookupValueResult = ""
    var turnaroundXrefLookupValueResult = ""
    var dataXrefSearchIdResult = ""
    var dataXrefSearchKeyResult = ""
    var dataXrefSearchValueResult = ""
    val jCredStash = JCredStash()

    init {
        when (ENVIRONMENT) {
            "test" -> {
                testDataXrefId = "3"
                testVendorXrefId = "1"
                testTurnaroundXrefId = "1"
                dataXrefLookupIdResult = "CampingWorldAPP"
                vendorXrefLookupIdResult = "quicktestAPP"
                turnaroundXrefLookupIdResult = "quicktest"
                dataXrefLookupKeyResult = "CAR"
                vendorXrefLookupKeyResult = "55681"
                turnaroundXrefLookupKey3Result = "501704"
                dataXrefLookupValueResult = "PP"
                vendorXrefLookupValueResult = "096WESTCOAST"
                turnaroundXrefLookupValueResult = "2008-12-10"
                dataXrefSearchIdResult = "CampingWorldAPP"
                dataXrefSearchKeyResult = "ALW"
                dataXrefSearchValueResult = "DF"
            }
        }
        token = jCredStash.getSecret("$ENVIRONMENT-credstash", "web.xref.api.identitytoken", null)
    }

    class TestDataXref {
        val data_xref_id: java.math.BigInteger? = null
        val lookup_id: String? = null
        val lookup_id2: String? = null
        val lookup_id3: String? = null
        val lookup_key: String? = null
        val lookup_key2: String? = null
        val lookup_key3: String? = null
        val lookup_value: String? = null
        val migration_source_systemid: String? = null
        val migration_source_uid: String? = null
        val owner_key: String? = null
    }

    class TestVendorXref {
        val created_by: String? = null
        val created_date: java.sql.Timestamp? = null
        val lookup_id: String? = null
        val lookup_key: String? = null
        val lookup_value: String? = null
        val lookup_value2: String? = null
        val migration_source_systemid: String? = null
        val migration_source_uid: String? = null
        val modified_by: String? = null
        val modified_date: java.sql.Timestamp? = null
        val row_version: Long? = null
        val vendor_xref_id: java.math.BigInteger? = null
    }

    class TestTurnaroundXref {
        val expiry_days: BigInteger? = null
        val id: BigInteger? = null
        val location: String? = null
        val lookup_id: String? = null
        val lookup_id2: String? = null
        val lookup_id3: String? = null
        val lookup_key: String? = null
        val lookup_key2: String? = null
        val lookup_key3: String? = null
        val lookup_key4: String? = null
        val lookup_key5: String? = null
        val lookup_key6: String? = null
        val lookup_value: String? = null
        val status: String? = null
        val supseded_by: String? = null
        val time_stamp: java.sql.Timestamp? = null
        val turnaround_time_stamp: java.sql.Timestamp? = null
    }

    @Test
    fun testGetDataXrefById() {
        testUrl += "/dataxrefs/" + testDataXrefId
        val result = StringBuilder()
        val url = URL(testUrl)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Authorization", "Bearer " + token)
        val rd = BufferedReader(InputStreamReader(conn.inputStream))
        val line = rd.readLine()
        result.append(line)
        rd.close()
        val dataXref = ObjectMapper().readValue<TestDataXref>(result.toString(), TestDataXref::class.java)
        assertEquals(dataXrefLookupIdResult, dataXref.lookup_id)
        assertEquals(dataXrefLookupKeyResult, dataXref.lookup_key)
        assertEquals(dataXrefLookupValueResult, dataXref.lookup_value)
    }

    @Test
    fun testGetVendorXrefById() {
        testUrl += "/vendorxrefs/" + testVendorXrefId
        val result = StringBuilder()
        val url = URL(testUrl)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Authorization", "Bearer " + token)
        val rd = BufferedReader(InputStreamReader(conn.inputStream))
        val line = rd.readLine()
        result.append(line)
        rd.close()
        val vendorXref = ObjectMapper().readValue<TestVendorXref>(result.toString(), TestVendorXref::class.java)
        assertEquals(vendorXrefLookupIdResult, vendorXref.lookup_id)
        assertEquals(vendorXrefLookupKeyResult, vendorXref.lookup_key)
        assertEquals(vendorXrefLookupValueResult, vendorXref.lookup_value)
    }

    @Test
    @Ignore
    fun testGetTurnaroundXrefById() {
        testUrl += "/turnaroundxrefs/" + testTurnaroundXrefId
        val result = StringBuilder()
        val url = URL(testUrl)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Authorization", "Bearer " + token)
        val rd = BufferedReader(InputStreamReader(conn.inputStream))
        val line = rd.readLine()
        result.append(line)
        rd.close()
        val turnaroundXref = ObjectMapper().readValue<TestTurnaroundXref>(result.toString(), TestTurnaroundXref::class.java)
        assertEquals(turnaroundXrefLookupIdResult, turnaroundXref.lookup_id)
        assertEquals(turnaroundXrefLookupKey3Result, turnaroundXref.lookup_key3)
        assertEquals(turnaroundXrefLookupValueResult, turnaroundXref.lookup_value)
    }

    @Test
    fun testEmptySearchDataXrefs() {
        testUrl += "/dataxrefs?page=0&count=100&sort=data_xref_id:asc"
        val result = StringBuilder()
        val url = URL(testUrl)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Authorization", "Bearer " + token)
        val rd = BufferedReader(InputStreamReader(conn.inputStream))
        var line: String? = ""
        while(line != null) {
            line = rd.readLine()
            result.append(line)
        }
        val dataXrefs = ObjectMapper().readValue(result.toString(), Array<TestDataXref>::class.java)
        rd.close()
        assertEquals(100, dataXrefs.size)
        assertEquals(dataXrefSearchIdResult, dataXrefs[0].lookup_id)
        assertEquals(dataXrefSearchKeyResult, dataXrefs[0].lookup_key)
        assertEquals(dataXrefSearchValueResult, dataXrefs[0].lookup_value)
    }

    @Test
    fun testSearchVendorXrefs() {
        testUrl += "/vendorxrefs?lookup_id=quicktestAPP&lookup_value=096WESTCOAST&page=1&count=100"
        val result = StringBuilder()
        val url = URL(testUrl)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Authorization", "Bearer " + token)
        val rd = BufferedReader(InputStreamReader(conn.inputStream))
        var line: String? = ""
        while(line != null) {
            line = rd.readLine()
            result.append(line)
        }
        val vendorXrefs = ObjectMapper().readValue(result.toString(), Array<TestVendorXref>::class.java)
        rd.close()
        assertEquals(1, vendorXrefs.size)
        assertEquals(vendorXrefLookupIdResult, vendorXrefs[0].lookup_id)
        assertEquals(vendorXrefLookupKeyResult, vendorXrefs[0].lookup_key)
        assertEquals(vendorXrefLookupValueResult, vendorXrefs[0].lookup_value)
    }

    @Test
    fun testUpdateDataXref() {
        testUrl += "/dataxrefs/" + testDataXrefId
        var result = StringBuilder()
        val url = URL(testUrl)
        var patchData = JSONObject()
        patchData.put("lookup_id", "TESTLOOKUPID")
        var conn = url.openConnection() as HttpURLConnection
        conn.doOutput = true
        setRequestMethod(conn, "PATCH")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Authorization", "Bearer " + token)
        var wr = conn.outputStream
        wr.write(patchData.toString().toByteArray())
        wr.flush()
        var rd = BufferedReader(InputStreamReader(conn.inputStream))
        var line = rd.readLine()
        result.append(line)
        var res = result.toString()
        assertEquals("""{"msg":"Successfully updated data xref","id":3}""", res)

        testUrl += "/dataxrefs/" + testDataXrefId
        result = StringBuilder()
        conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Authorization", "Bearer " + token)
        rd = BufferedReader(InputStreamReader(conn.inputStream))
        line = rd.readLine()
        result.append(line)
        rd.close()
        val dataXref = ObjectMapper().readValue<TestDataXref>(result.toString(), TestDataXref::class.java)
        assertEquals("TESTLOOKUPID", dataXref.lookup_id)
        assertEquals(dataXrefLookupKeyResult, dataXref.lookup_key)
        assertEquals(dataXrefLookupValueResult, dataXref.lookup_value)

        // Revert the update
        testUrl += "/dataxrefs/" + testDataXrefId
        result = StringBuilder()
        patchData = JSONObject()
        patchData.put("lookup_id", dataXrefLookupIdResult)
        conn = url.openConnection() as HttpURLConnection
        conn.doOutput = true
        setRequestMethod(conn, "PATCH")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Authorization", "Bearer " + token)
        wr = conn.outputStream
        wr.write(patchData.toString().toByteArray())
        wr.flush()
        rd = BufferedReader(InputStreamReader(conn.inputStream))
        line = rd.readLine()
        result.append(line)
        res = result.toString()
        assertEquals("""{"msg":"Successfully updated data xref","id":3}""", res)
    }

    @Test
    fun testUpdateVendorXref() {
        testUrl += "/vendorxrefs/" + testVendorXrefId
        var result = StringBuilder()
        val url = URL(testUrl)
        var patchData = JSONObject()
        patchData.put("lookup_id", "UNIQUETESTLOOKUPID")
        var conn = url.openConnection() as HttpURLConnection
        conn.doOutput = true
        setRequestMethod(conn, "PATCH")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Authorization", "Bearer " + token)
        var wr = conn.outputStream
        wr.write(patchData.toString().toByteArray())
        wr.flush()
        var rd = BufferedReader(InputStreamReader(conn.inputStream))
        var line = rd.readLine()
        result.append(line)
        var res = result.toString()
        assertEquals("""{"msg":"Successfully updated vendor xref","id":1}""", res)

        testUrl += "/vendorxrefs/" + testVendorXrefId
        result = StringBuilder()
        conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Authorization", "Bearer " + token)
        rd = BufferedReader(InputStreamReader(conn.inputStream))
        line = rd.readLine()
        result.append(line)
        rd.close()
        val vendorXref = ObjectMapper().readValue<TestVendorXref>(result.toString(), TestVendorXref::class.java)
        assertEquals("UNIQUETESTLOOKUPID", vendorXref.lookup_id)
        assertEquals(vendorXrefLookupKeyResult, vendorXref.lookup_key)
        assertEquals(vendorXrefLookupValueResult, vendorXref.lookup_value)

        // Revert the update
        testUrl += "/vendorxrefs/" + testVendorXrefId
        result = StringBuilder()
        patchData = JSONObject()
        patchData.put("lookup_id", vendorXrefLookupIdResult)
        conn = url.openConnection() as HttpURLConnection
        conn.doOutput = true
        setRequestMethod(conn, "PATCH")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Authorization", "Bearer " + token)
        wr = conn.outputStream
        wr.write(patchData.toString().toByteArray())
        wr.flush()
        rd = BufferedReader(InputStreamReader(conn.inputStream))
        line = rd.readLine()
        result.append(line)
        res = result.toString()
        assertEquals("""{"msg":"Successfully updated vendor xref","id":1}""", res)
    }


    @Test
    fun testCreateAndDeleteDataXref() {
        testUrl += "/dataxrefs"
        var result = StringBuilder()
        var url = URL(testUrl)
        var postData = JSONObject()
        postData.put("lookup_id", "TESTLOOKUPID")
        postData.put("lookup_id2", "TESTLOOKUPID2")
        postData.put("lookup_id3", "TESTLOOKUPID3")
        postData.put("lookup_key", "TESTLOOKUPKEY")
        postData.put("lookup_key2", "TESTLOOKUPKEY2")
        postData.put("lookup_key3", "TESTLOOKUPKEY3")
        postData.put("lookup_key3", "TESTLOOKUPVALUE")
        var conn = url.openConnection() as HttpURLConnection
        conn.doOutput = true
        conn.requestMethod = "POST"
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Authorization", "Bearer " + token)
        var wr = conn.outputStream
        wr.write(postData.toString().toByteArray())
        wr.flush()
        var rd = BufferedReader(InputStreamReader(conn.inputStream))
        var line = rd.readLine()
        result.append(line)
        var res = result.toString()
        val createdXrefId = res.substring(45, res.length-1)
        assertEquals("{\"msg\":\"Successfully created data xref\",\"id\":", res.substring(0, 45))

        // Delete the xref that was just created
        testUrl += "/" + createdXrefId
        result = StringBuilder()
        url = URL(testUrl)
        conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "DELETE"
        conn.setRequestProperty("Authorization", "Bearer " + token)
        rd = BufferedReader(InputStreamReader(conn.inputStream))
        line = rd.readLine()
        result.append(line)
        res = result.toString()
        assertEquals("{\"msg\":\"Successfully deleted data xref\",\"id\":$createdXrefId}", res)
    }

    @Test
    fun testCreateAndDeleteVendorXref() {
        testUrl += "/vendorxrefs"
        var result = StringBuilder()
        var url = URL(testUrl)
        var postData = JSONObject()
        postData.put("created_by", "TESTCREATEDBY")
        postData.put("lookup_id", "TESTLOOKUPID")
        postData.put("lookup_key", "TESTLOOKUPKEY")
        postData.put("lookup_value", "TESTLOOKUPVALUE")
        postData.put("lookup_value2", "TESTLOOKUPVALUE2")
        postData.put("modified_by", "TESTMODIFIEDBY")
        var conn = url.openConnection() as HttpURLConnection
        conn.doOutput = true
        conn.requestMethod = "POST"
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Authorization", "Bearer " + token)
        var wr = conn.outputStream
        wr.write(postData.toString().toByteArray())
        wr.flush()
        var rd = BufferedReader(InputStreamReader(conn.inputStream))
        var line = rd.readLine()
        result.append(line)
        var res = result.toString()
        val createdXrefId = res.substring(47, res.length-1)
        assertEquals("{\"msg\":\"Successfully created vendor xref\",\"id\":", res.substring(0, 47))

        // Delete the xref that was just created
        testUrl += "/" + createdXrefId
        result = StringBuilder()
        url = URL(testUrl)
        conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "DELETE"
        conn.setRequestProperty("Authorization", "Bearer " + token)
        rd = BufferedReader(InputStreamReader(conn.inputStream))
        line = rd.readLine()
        result.append(line)
        res = result.toString()
        assertEquals("{\"msg\":\"Successfully deleted vendor xref\",\"id\":$createdXrefId}", res)
    }


    // Helper method to set the request method on an httpurlconnection
    // Used for PATCH since httpurlconnection class doesnt natively support the PATCH method
    private fun setRequestMethod(c: HttpURLConnection, value: String) {
        try {
            val target: Any
            if (c is HttpsURLConnectionImpl) {
                val delegate = HttpsURLConnectionImpl::class.java.getDeclaredField("delegate")
                delegate.isAccessible = true
                target = delegate.get(c)
            } else {
                target = c
            }
            val f = HttpURLConnection::class.java.getDeclaredField("method")
            f.isAccessible = true
            f.set(target, value)
        } catch (ex: IllegalAccessException) {
            throw AssertionError(ex)
        } catch (ex: NoSuchFieldException) {
            throw AssertionError(ex)
        }
    }
}
