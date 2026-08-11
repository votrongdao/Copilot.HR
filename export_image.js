const { chromium } = require('playwright');
const path = require('path');
const fs = require('fs');

// Shared Wireframe Design System CSS for partial HTML snippets
const COMMON_DESIGN_SYSTEM = `
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #09090b;
            --primary-hover: #27272a;
            --primary-active: #18181b;
            --primary-light: #f4f4f5;
            --primary-subtle: #e4e4e7;
            --primary-border: #71717a;
            --bg-app: #fafafa;
            --bg-card: #ffffff;
            --bg-hover: #f4f4f5;
            --text-main: #09090b;
            --text-muted: #71717a;
            --text-light: #a1a1aa;
            --border-color: #e4e4e7;
            --border-strong: #18181b;
            --border-dashed: #d4d4d8;
            --radius-sm: 6px;
            --radius-md: 10px;
            --radius-lg: 14px;
            --shadow-wireframe: 0 1px 3px rgba(0, 0, 0, 0.05);
            --shadow-hover: 0 4px 12px rgba(0, 0, 0, 0.08);
        }
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif !important;
        }
        body {
            background: #ffffff;
            color: var(--text-main);
            padding: 24px;
            display: inline-block;
        }
    </style>
`;

function getAllHtmlFiles(dirPath, arrayOfFiles = []) {
    const files = fs.readdirSync(dirPath);

    files.forEach(file => {
        const fullPath = path.join(dirPath, file);
        if (fs.statSync(fullPath).isDirectory()) {
            if (file !== 'images' && file !== 'node_modules' && file !== 'components' && file !== 'js') {
                getAllHtmlFiles(fullPath, arrayOfFiles);
            }
        } else if (file.endsWith('.html')) {
            arrayOfFiles.push(fullPath);
        }
    });

    return arrayOfFiles;
}

(async () => {
    console.log('🚀 Starting Smart HTML-to-Image Exporter (Auto-Crop Component Modals/Drawers)...');

    const iaHtmlDir = path.resolve(__dirname, 'IA/people_management');
    const outputDir = path.resolve(__dirname, 'images/uiux');

    if (!fs.existsSync(iaHtmlDir)) {
        console.error(`❌ HTML directory not found at: ${iaHtmlDir}`);
        process.exit(1);
    }

    if (!fs.existsSync(outputDir)) {
        fs.mkdirSync(outputDir, { recursive: true });
        console.log(`📁 Created output folder: ${outputDir}`);
    }

    const htmlFiles = getAllHtmlFiles(iaHtmlDir);
    console.log(`🔍 Found ${htmlFiles.length} HTML file(s) to export into images/uiux:\n`);

    const browser = await chromium.launch({ headless: true });
    const context = await browser.newContext({
        viewport: { width: 1920, height: 1080 },
        deviceScaleFactor: 2
    });

    let successCount = 0;

    for (const filePath of htmlFiles) {
        const fileBasename = path.basename(filePath, '.html');
        const outputImagePath = path.join(outputDir, `${fileBasename}.png`);
        const isDetailSnippet = filePath.includes('detail');
        const fileContent = fs.readFileSync(filePath, 'utf-8');

        const page = await context.newPage();

        try {
            console.log(`📸 Exporting & Tight-Cropping: [${fileBasename}.html] -> [${fileBasename}.png] ...`);

            if (isDetailSnippet) {
                if (!fileContent.includes('<!DOCTYPE html>')) {
                    // Wrap partial snippet with Inter font + design system container
                    const wrappedHtml = `
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            ${COMMON_DESIGN_SYSTEM}
                        </head>
                        <body>
                            <div id="export-container" style="display: inline-block; background: #ffffff; min-width: 480px; padding: 20px; border: 1px solid #e4e4e7; border-radius: 10px;">
                                ${fileContent}
                            </div>
                        </body>
                        </html>
                    `;
                    await page.setContent(wrappedHtml, { waitUntil: 'networkidle' });
                    await page.waitForTimeout(300);

                    const element = await page.$('#export-container');
                    if (element) {
                        await element.screenshot({ path: outputImagePath, type: 'png' });
                    } else {
                        await page.screenshot({ path: outputImagePath, fullPage: true, type: 'png' });
                    }
                } else {
                    // Full HTML snippet document: Load URL and crop tightly to main card/modal/drawer
                    const fileUrl = `file:///${filePath.replace(/\\/g, '/')}`;
                    await page.goto(fileUrl, { waitUntil: 'networkidle', timeout: 30000 });
                    await page.waitForTimeout(400);

                    // Find actual component bounding box element
                    const componentElement = await page.$('.modal-card, .drawer-preview, .drawer-content, .form-card, .profile-card, .timeline-card, .card, body > div');

                    if (componentElement) {
                        await componentElement.screenshot({ path: outputImagePath, type: 'png' });
                    } else {
                        await page.screenshot({ path: outputImagePath, fullPage: true, type: 'png' });
                    }
                }
            } else {
                // Main Full Page Screen
                const fileUrl = `file:///${filePath.replace(/\\/g, '/')}`;
                await page.goto(fileUrl, { waitUntil: 'networkidle', timeout: 30000 });
                await page.waitForTimeout(500);

                await page.screenshot({
                    path: outputImagePath,
                    fullPage: true,
                    type: 'png'
                });
            }

            console.log(`   ✅ Saved -> ${path.relative(__dirname, outputImagePath)}`);
            successCount++;
        } catch (err) {
            console.error(`   ❌ Failed to export [${fileBasename}]:`, err.message);
        } finally {
            await page.close();
        }
    }

    await browser.close();

    console.log(`\n🎉 Complete! Exported and tight-cropped ${successCount}/${htmlFiles.length} images to ${outputDir}`);
})();
