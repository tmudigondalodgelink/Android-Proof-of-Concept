import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.network.http.DefaultHttpEngine
import com.apollographql.apollo3.network.okHttpClient
import com.example.datamodule.client.IGraphQLClient
import com.example.domainmodule.errors.DataError
import com.example.domainmodule.errors.ExecutionError
import okhttp3.OkHttpClient
import okio.Buffer
import java.util.concurrent.TimeUnit.SECONDS

class GraphQLClient: IGraphQLClient {
    private val PLATFORM_TYPE_HEADER = "platform-type"
    private val PLATFORM_TYPE_HEADER_VALUE = "Android"
    var accountToken: String = ""
        get() = field
        set(value) {
            field = value
            okHttpClient = buildOkHttpClient(value)
            apolloClient = buildApolloClient()
        }

    var serverUrl = "https://test-api.lodgelink.com/graphql"
        get() = field
        set(value) {
            field = value
            apolloClient = buildApolloClient()
        }

    fun reset() {
        accountToken = ""
        serverUrl = "https://test-api.lodgelink.com/graphql"
    }

    private var okHttpClient = buildOkHttpClient()

    private var apolloClient = buildApolloClient()

    private fun buildOkHttpClient(token: String = ""): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .callTimeout(180L, SECONDS)
            .connectTimeout(180L, SECONDS)
            .readTimeout(180L, SECONDS)
            .writeTimeout(180L, SECONDS)
        if (!token.isEmpty()) {
            builder.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(PLATFORM_TYPE_HEADER, PLATFORM_TYPE_HEADER_VALUE)
                    .addHeader("Authorization", "bearer $token")
                    .build()

                chain.proceed(request)
            }
        }
        return builder
            .addInterceptor { chain ->
                val request = chain.request()
                val buffer = Buffer()
                request.body?.writeTo(buffer)
                Log.d("REQUEST", "${request}\n${buffer.readUtf8()}")
                chain.proceed(request)
            }
            .addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                val responseBody = response.peekBody(4096).string()
                Log.d(
                    "RESPONSE",
                    "${response}\n$responseBody"
                )
                response
            }
            .build()
    }
    private fun buildApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(serverUrl)
            .httpEngine(DefaultHttpEngine(timeoutMillis = 600_000))
            .okHttpClient(okHttpClient)
            .build()
    }

    override suspend fun <D : Query.Data> query(query: Query<D>): D? {
        val response = apolloClient.query(query).execute()

        if (response.hasErrors()) {
            response.errors?.let {
                val error = it[0]
                Log.e("GRAPHQL QUERY ERROR", error.toString())
                throw DataError.NetworkError(ExecutionError.WithMessage(error.message))
            }
        }

        return response.data
    }

    override suspend fun <D : Mutation.Data> mutate(mutation: Mutation<D>): D? {
        val response = apolloClient.mutation(mutation).execute()
        if (response.hasErrors()) {
            response.errors?.let {
                val error = it[0]
                Log.e("GRAPHQL MUTATION ERROR", error.toString())
                throw DataError.NetworkError(ExecutionError.WithMessage(error.message))
            }
        }
        return response.data
    }
}