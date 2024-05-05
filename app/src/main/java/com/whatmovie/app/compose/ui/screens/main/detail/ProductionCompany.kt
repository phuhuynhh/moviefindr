package com.whatmovie.app.compose.ui.screens.main.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.whatmovie.app.compose.R
import com.whatmovie.app.compose.domain.models.ProductionCompany
import com.whatmovie.app.compose.util.Image

@Composable
fun ProductionCompanyList(companies: List<ProductionCompany>, size: Int = 48) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.production_companies),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 6.dp)
        )

        companies.forEach { company ->
            ProductionCompanyRow(company = company, size)
        }
    }
}

@Composable
fun ProductionCompanyRow(company: ProductionCompany, size: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(6.dp)
                .height(50.dp)
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
            ) {
                // Display company logo if available
                if (!company.logoPath.isNullOrBlank()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(Image.buildURL(company.logoPath!!, Image.ImageType.LOGO))
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.FillWidth,
                        placeholder = object : Painter() {
                            override val intrinsicSize get() = Size(100f, 100f)

                            override fun DrawScope.onDraw() {
                                drawRect(Color.DarkGray)
                            }
                        },
                        error = painterResource(id = R.drawable.ic_building_office),
                        contentDescription = company.name,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .size(size.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                } else {
                    // Placeholder image if logo is not available
                    Image(
                        painter = painterResource(id = R.drawable.ic_building_office),
                        contentDescription = "Company Logo Placeholder",
                        alignment = Alignment.Center,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(size.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Display company name and origin country
                Text(
                    text = company.name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                    // Adjust text style as needed
                )
                Text(
                    text = "Origin Country: ${company.originCountry}",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSecondaryContainer),
                    maxLines = 1,
                    // Adjust text style as needed
                )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun ProductionCompanyListPreview() {
    val companies = listOf(
        ProductionCompany(
            id = 882,
            logoPath = "/iDw9Xxok1d9WAM2zFicI8p3khTH.png",
            name = "TOHO",
            originCountry = "JP"
        ),
        ProductionCompany(
            id = 182161,
            logoPath = "/wvG4lK0m76M6jK8WbWkXNecA7SP.png",
            name = "TOHO Studios",
            originCountry = "JP"
        ),
        ProductionCompany(
            id = 12386,
            logoPath = "/oxvw2Mrq3GcTxFc2mlT7E5tpek7.png",
            name = "Robot Communications",
            originCountry = "JP"
        )
    )
    ProductionCompanyList(companies = companies)
}