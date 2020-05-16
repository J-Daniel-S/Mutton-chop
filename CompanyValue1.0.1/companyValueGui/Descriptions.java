package companyValueGui;

public class Descriptions {

	public final static String price = "Current Price:\n\nThis was the stocks price the last the program was able to query the API.";

	public final static String fcfGrowth = "Free Cash Flow Growth:\n\nIf this number seems implausible it may not be trustworthy.  Swings from negative to positive fcf growth from year over year can cause strange"
			+ " results.  Check against fcf growth numbers from Yahoo Finance, etc.\n\nTo see the actual �earnings� for the business, and ultimately for the owner of XYZ Company (that is, the �owner earnings� as Buffett calls them), "
			+ "we start at the top�Cash Flows from Operating Activities, or the amount of cash generated by the day-to-day operations of the business.\n"
			+ "\nOwner Earnings = Net Income + Depreciation and Amortization + Non-Cash Charges � Average Capital Expenditures\n\nor\n"
			+ "\nFree Cash Flow = Cash Flow From Operations - Capital Expenditures"
			+ "\n\nPonzio, Joel. F Wall Street: Joe Ponzio's No-Nonsense Approach to Value Investing For the Rest of Us (p. 76). Adams Media. Kindle Edition.";

	public final static String autoUpdate10k = "Auto Update:\n\nIf data is manually entered this should remain disabled.  Enabling this may cause you to "
			+ "have to enter the data again or may cause the program to crash.  This governs continuous updates of price and market capitalization"
			+ "and the updating of the annual reports.\n"
			+ "This is when the data from the program was announced by the company.  Public companies must report this once a year.  The program"
			+ " checks automatically for updates to this report if auto update is toggled.";

	public final static String currentPrice = "Current Price:\n\nThis is the price each share of stock was selling for at the point this program was last updated";

	public final static String mCap = "Market Capitalization:\n\n[M]arket capitalization�the price at which you could theoretically purchase the entire company. "
			+ "Market capitalization is equal to the number of shares of stock outstanding multiplied by the current price the stock sells for.\r\n"
			+

			"Ponzio, Joel. F Wall Street: Joe Ponzio's No-Nonsense Approach to Value Investing For the Rest of Us (p. 98). Adams Media. Kindle Edition.";

	public final static String sharesOut = "Outstanding shares:\n\nThe number of shares available for purchase on the stock market.";

	public final static String sector = "Sector:\n\nA sector is an area of the economy in which businesses share the same or a related product or service. It can also be thought of as an industry or market that shares common operating characteristics. "
			+ "Dividing an economy into different sectors allows for more in-depth analysis of the economy as a whole.\n\nUnderstanding Sectors\r\n"
			+ "Almost all economies are comprised of four, high-level sectors, which, in turn, are each made up of smaller sectors. Of the large sectors within an economy, the first group is called the primary sector and involves companies that"
			+ " participate in the extraction and harvesting of natural products from the earth, such as agriculture, mining and forestry. The secondary sector consists of processing, manufacturing and construction companies. The tertiary sector"
			+ " is comprised of companies that provide services, such as retailers, entertainment firms and financial organizations. The quaternary sector includes companies in the intellectual pursuits, such as educational businesses.\r\n"
			+ "\r\n" + "Copied from https://www.investopedia.com/terms/s/sector.asp";

	public final static String industry = "Industry:\n\nAn industry is a group of companies that are related based on their primary business activities. In modern economies, there are dozens of industry classifications, which are typically grouped into larger categories called sectors.\r\n"
			+ "\r\n"
			+ "Individual companies are generally classified into an industry based on their largest sources of revenue. For example, while an automobile manufacturer might have a financing division that contributes 10% to the firm's overall revenues,"
			+ " the company would be classified in the automaker industry by most classification systems."
			+ "\n\nCopied from https://www.investopedia.com/terms/i/industry.asp";

	public final static String tenK = "Date of last annual report (10k):\n\nThis is when the company last released an annual report.";

	public final static String profitMargin = "Profit Margin:\n\n\"Calculated as earnings divided by sales, the profit margin tells us how much of each dollar of sales is converted into earnings. Companies that are expected to have low (or thin) profit margins and low inventory turnovers should generally be avoided.\"\r\n"
			+ "\r\n"
			+ "Ponzio, Joel. F Wall Street: Joe Ponzio's No-Nonsense Approach to Value Investing For the Rest of Us (p. 243). Adams Media. Kindle Edition. "
			+ "\n\nFor the sake of this program cash flow from operations is used rather than earnings, as earnings includes non-cash charges that do not"
			+ " significantly affect a company's operations."
			+ "\n\n\"Earnings Also called �Net Income.� A company�s after-tax income as stated on its tax return. Wall Street focuses heavily on earnings; "
			+ "intelligent investors know that earnings can be easily manipulated and instead focus on owner earnings [or cash flow]\".\r\n"
			+ "\r\n"
			+ "Ponzio, Joel. F Wall Street: Joe Ponzio's No-Nonsense Approach to Value Investing For the Rest of Us (p. 243). Adams Media. Kindle Edition.";

	public final static String croic = "Cash Return on Invested Capital (CROIC):\n\nThe amount of cash that the business generates for each dollar of capital"
			+ " that the owners have invested. Over the course of many decades, a business will only grow as quickly as its long-term average CROIC.\r\n"
			+ "\r\n"
			+ "Ponzio, Joel. F Wall Street: Joe Ponzio's No-Nonsense Approach to Value Investing For the Rest of Us (p. 243). Adams Media. Kindle Edition.";

	public final static String rOIC = "Return on Invested Capital:\n\n\"Return on Equity Also called �ROE.� Calculated as earnings divided by Shareholder Equity, "
			+ "return on equity tells investors how well a company can generate earnings based on the size of its net worth. Assuming that earnings have not been "
			+ "manipulated, investors should choose a business with a high ROE versus a business with a low ROE\".\r\n"
			+ "\r\n"
			+ "Ponzio, Joel. F Wall Street: Joe Ponzio's No-Nonsense Approach to Value Investing For the Rest of Us (p. 243). Adams Media. Kindle Edition.\n"
			+ "\nThis program adds long term debt to equity for the purposes of calculating this, much the same as in CROIC.";

	public final static String rOA = "Return on Assets:\n\nThe ratio as calculated for Return On Invested Capital, but for tangible assets owned by the company. "
			+ " This ratio does not include goodwill (oversimplified definition: acquired brand recognition) or intangible assets (patents, etc.).";

	public final static String fcf = "Free Cash Flow:\n\nThe cash that a business can generate for its silent partners. He stated, �. . .we consider the owner earnings figure, not [net income or earnings], to be the relevant item for valuation purposes�both for investors in buying stocks and for managers in buying entire businesses.�\r\n"
			+ " He then went on to give his formula for calculating owner earnings:\n\nOwner Earnings = Net Income + Depreciation and Amortization + Non-Cash Charges � Average Capital Expenditures\r\n"
			+ "\r\n"
			+ "Ponzio, Joel. F Wall Street: Joe Ponzio's No-Nonsense Approach to Value Investing For the Rest of Us (p. 83). Adams Media. Kindle Edition. "
			+ "\n\nThis program refers to this as free cash flow";

	public final static String changeInFcf = "Change in Free Cash Flow:\n\nThis is the growth (or decline) of free cash flow year over year. It is used to determine the rate "
			+ "at which a company will grow.";

	public final static String coh = "Cash on hand:\n\nThe amount of cash or cash equivalents a company has on hand.";

	public final static String eDRatio = "Equity/Debt Ratio:\n\nThis indicates how much of a company's capital comes from equity as opposed to debt.";

	public final static String changeInDebt = "Change in debt:\n\nHow much the debt held by the company has grown or shrank over the past year of operations.";

	public final static String cDRatio = "Cash to debt ratio:\n\nThe ratio of cash on hand to long term debt held by the company.  This can give you an idea of a company's"
			+ " survivability should something occur that severely hampers their ability to generate cash.";

	public final static String buyAndHold = "Buy and hold fair value:\n\n\"The Buy-and-Hold Valuation\n\nThe cash yield method, as I�ve said, is quick and dirty and has important uses. "
			+ "But it has two fundamental flaws. First, it is often useless for rapidly growing companies. Second, it ignores the net worth of a business. "
			+ "Some companies, by their very operation, have huge net worths but generate relatively small amounts of cash in relation to their size. If we"
			+ " just look at cash, a business generating $10 million of excess cash is twice as valuable as a business generating $5 million of excess cash "
			+ "(all other things being equal).  But we can�t ignore the assets. If the second business had a net worth 100 times the size of the first, would it "
			+ "still be less valuable? That is, is a $100 million portfolio worth less than a $1 million portfolio just because the $1 million portfolio"
			+ " generates more income? The buy-and-hold (BAH) method looks at the value of a business by taking into account the value of the net worth and"
			+ " the value of the future cash. Where the cash yield method asks, �Is this cheap today compared to other returns I could get?,� "
			+ "the buy-and-hold method seeks to answer the question, �How much is it worth if I want to buy the entire business?� The buy-and-hold method of "
			+ "valuation takes into account the assets and the earning power of the business. If a company has a huge net worth but closes down operations and will not "
			+ "generate owner earnings in the future, it still has a value in that its assets could be liquidated, its debts paid off, and the remaining cash�the net worth�could "
			+ "be distributed to shareholders. The quick and dirty cash yield method won�t tell you that. The BAH formula uses the net worth as a starting point and it seeks to predict "
			+ "cash flows twenty years into the future. (Beyond twenty years, the numbers do not have much of an impact on the valuation. So, the cash flows expected in twenty-seven years would have little bearing on the value today.)\r\n"
			+ "\nPonzio, Joel. F Wall Street: Joe Ponzio's No-Nonsense Approach to Value Investing For the Rest of Us (p. 103). Adams Media. Kindle Edition.\"";

	public final static String safeValue = "Buy and hold safe value:\n\nThe buy and hold fair market value of each share adjusted for desired margin of safety (e.g. the price below which you should purchase the stock at the desired margin of safety).";

	public final static String currentMarginOfSafety = "Current margin of safety:\n\nThe percentage below (or above) the buy and hold fair value each share is selling for."
			+ "  A negative value indicates the stock is selling for more than buy and hold suggests it is worth.";

	public final static String mos = "Margin of Safety:\n\nThe difference between the intrinsic value of a company and the price of its stock.\n\n"
			+ "\"Q I�m still confused. How does the margin of safety affect my returns? A Over the long term, a company�s stock price will generally follow the business�s value. "
			+ "If the company grows at an average annual rate of 7 percent for ten years, you can expect to see the stock price grow at roughly 7 percent over those ten years if you measure "
			+ "it under �normal� market circumstances. If the company is priced rationally at $100 today and grows at 7 percent a year for ten years, you would expect the price to be about"
			+ " $197 in the future�assuming normal market conditions. (Remember, the markets are generally efficient�or �normal��most of the time.) An investor buying at $100 would earn an "
			+ "average annual return of 7 percent. An investor buying at $150�during times of great joy and excitement�would earn an average annual return of just 3 if the price were �normal�"
			+ " again in ten years. An intelligent investor would earn an average annual return of 15 percent. All three bought the same company. The price in ten years was the same for all of them. "
			+ "Their results were drastically different. The price you pay determines your returns.\"\n\n"
			+ "Ponzio, Joel. F Wall Street: Joe Ponzio's No-Nonsense Approach to Value Investing For the Rest of Us (p. 243). Adams Media. Kindle Edition.";

	public final static String cashYield = "Cash Yield:\n\nA quick and dirty method for valuing companies. A high cash yield implies that a business is selling at a discount to intrinsic value (assuming the business will grow or, at the very least, remain fairly stable).\r\n"
			+ "\r\n"
			+ "Ponzio, Joel. F Wall Street: Joe Ponzio's No-Nonsense Approach to Value Investing For the Rest of Us (p. 243). Adams Media. Kindle Edition. "
			+ "\n\nThe cash yield is a quick-and-dirty valuation to see if a company is a real steal, but it is not a make-or-break valuation. The buy-and-hold method will give you better insight into the intrinsic value but requires more time. Keep in mind: Investing should be simple. If you find a company selling for two, three, or four times owner earnings (that is, a cash yield of 25 percent or more) and you expect owner earnings to continue, you don�t need to do any further valuation.\r\n"
			+ "\r\n"
			+ "Ponzio, Joel. F Wall Street: Joe Ponzio's No-Nonsense Approach to Value Investing For the Rest of Us (p. 110). Adams Media. Kindle Edition. \n"
			+ "\nThe cash yield method . . . has two fundamental flaws. First, it is often useless for rapidly growing companies. Second,"
			+ " it ignores the net worth of a business. Some companies, by their very operation, have huge net worths but generate relatively small amounts of cash in relation to their size. If we"
			+ " just look at cash, a business generating $10 million of excess cash is twice as valuable as a business generating $5 million of excess cash (all other things being equal).\r\n"
			+ "\r\n"
			+ "Ponzio, Joel. F Wall Street: Joe Ponzio's No-Nonsense Approach to Value Investing For the Rest of Us (pp. 102-103). Adams Media. Kindle Edition.";

	public final static String whyCroicOrCashFlow = "Dramatic changes in free cash flow can lead to inaccurate future values.  CROIC may provide"
			+ "a more accurate valuation if cash flows have been erratic.";

	public final static String dividendsPerShare = "Dividends per share:\n\nThe dollar amount of dividends paid per share owned by each stock holder";

	public final static String capitalEfficienty = "Capital Efficiency\n\nThis one is a custom metric taught me by a retired stock broker.\n\n"
			+ "\"Next is something called capital efficiency. All you're trying to understand with this test is how much capital the company requires to maintain its facilities and grow its revenues. "
			+ "For example, oil and gas companies are notorious for spending every penny they make on drilling more holes and building more facilities. Their capital-spending programs leave little of their"
			+ " profits to be distributed to shareholders (often less than zero). All you need to do is figure out whether the company in question distributes more capital back to shareholders� or spends more money"
			+ " \"on itself\" via capital-spending programs. A great business is able to distribute more profits to its shareholders than it consumes via capital investments. Coke, for example, spent $2.4 billion on"
			+ " capital investments (expenditure) in its own business in 2014. It spent $5.35 billion on dividends and $2.63 billion on share buybacks in the same period (Under cash from financing on Cash Flow). You can"
			+ " see that Coke is spending far more on its shareholders than it spends on itself. (By the way, all of these numbers are labeled clearly on the cash flow statement I linked to earlier.)\"";

}
